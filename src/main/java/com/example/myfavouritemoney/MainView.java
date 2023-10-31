package com.example.myfavouritemoney;

import com.example.myfavouritemoney.controller.MoneyOperationController;
import com.example.myfavouritemoney.controller.MonitoredCategoryController;
import com.example.myfavouritemoney.controller.WalletController;
import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.dto.MonitoredCategoryDTO;
import com.example.myfavouritemoney.dto.WalletDTO;
import com.example.myfavouritemoney.enums.OperationRegularity;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.select.data.SelectDataView;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "")
@Component
public class MainView extends VerticalLayout {

    private WalletController walletController;
    private MoneyOperationController moneyOperationController;

    private MonitoredCategoryController monitoredCategoryController;

    @Autowired
    public MainView( WalletController walletController,
                     MoneyOperationController moneyOperationController,
                     MonitoredCategoryController monitoredCategoryController) {
        this.walletController = walletController;
        this.moneyOperationController = moneyOperationController;
        this.monitoredCategoryController = monitoredCategoryController;

        //Мои кошельки
        VerticalLayout walletsList = new VerticalLayout();
        walletsList.add(new H3("Мои кошельки:"));

        var wallets = walletController.getWallets();
        var sum = (Double) wallets.stream().map(WalletDTO::getMoney).mapToDouble(Float::doubleValue).sum();

        Grid<WalletDTO> walletGrid = new Grid<>(WalletDTO.class, false);
        walletGrid.addColumn(WalletDTO::getName).setHeader("Имя кошелька");
        walletGrid.addColumn(WalletDTO::getType).setHeader("Тип");
        walletGrid.addColumn(WalletDTO::getMoney).setHeader("Сумма").setFooter(sum + "₽");
        walletGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        walletGrid.setItems(wallets);
        walletsList.add(walletGrid);


        //Расходы за месяц
        var h3 = new SpecialH3(LocalDateTime.now().getMonth());

        VerticalLayout expensesList = new VerticalLayout();
        expensesList.add(h3);
        SpecialGrid<MoneyOperationDTO> moneyOperationDTOGrid = new SpecialGrid<>(MoneyOperationDTO.class, false);
        moneyOperationDTOGrid.initGrid(0, h3);
        moneyOperationDTOGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);


        expensesList.add(moneyOperationDTOGrid);
        h3.addClickListener((ComponentEventListener<ClickEvent<H3>>) h3ClickEvent -> {

            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Выбор месяца");

            Button previousMonthButton = new Button(LocalDateTime.now().minusMonths(1).getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")), e -> {
                h3.setText(LocalDateTime.now().minusMonths(1).getMonth());
                moneyOperationDTOGrid.initGrid(-1, h3);
                dialog.close();
            });
            Button currentMonthButton = new Button(LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")), e -> {
                h3.setText(LocalDateTime.now().getMonth());
                moneyOperationDTOGrid.initGrid(0, h3);
                dialog.close();
            });
            Button upComingMonthButton = new Button(LocalDateTime.now().plusMonths(1).getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")), e -> {
                h3.setText(LocalDateTime.now().plusMonths(1).getMonth());
                moneyOperationDTOGrid.initGrid(1, h3);
                dialog.close();
            });

            dialog.getFooter().add(previousMonthButton);
            dialog.getFooter().add(currentMonthButton);
            dialog.getFooter().add(upComingMonthButton);
            dialog.open();
        });


        //Контролируемые категории

        VerticalLayout monitoredCategoriesList = new VerticalLayout();
        monitoredCategoriesList.add(new H3("Контролируемые категории:"));

        var monitoredCategories = monitoredCategoryController.getCategories(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue());

        Grid<MonitoredCategoryDTO> monitoredCategoriesGrid = new Grid<>(MonitoredCategoryDTO.class, false);
        monitoredCategoriesGrid.addColumn(MonitoredCategoryDTO::getName).setHeader("Имя");
        monitoredCategoriesGrid.addColumn(MonitoredCategoryDTO::getCurrentExpense).setHeader("Текущие расходы");
        monitoredCategoriesGrid.addColumn(MonitoredCategoryDTO::getMonthLimit).setHeader("Лимит на месяц");
        monitoredCategoriesGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        monitoredCategoriesGrid.setItems(monitoredCategories);
        monitoredCategoriesList.add(monitoredCategoriesGrid);



        //Общее
        HorizontalLayout mainLayo = new HorizontalLayout();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addAndExpand(walletsList);
        verticalLayout.addAndExpand(monitoredCategoriesList);

        VerticalLayout verticalLayout2 = new VerticalLayout();
        verticalLayout2.addAndExpand(expensesList);
        mainLayo.addAndExpand(verticalLayout);
        mainLayo.addAndExpand(verticalLayout2);

        add(
                new H1("My Favourite Money"),
                mainLayo
        );
    }

    private Renderer<MoneyOperationDTO> createEmployeeRenderer(SpecialGrid grid, int plusMonth, SpecialH3 h3, Editor editor) {
        return LitRenderer.<MoneyOperationDTO> of(
                        "<vaadin-checkbox @click=\"${handleCheckClick}\" ?checked=\"${item.checked}\"></vaadin-checkbox>")
                .withProperty("checked", MoneyOperationDTO::getCompleted)
                .withFunction("handleCheckClick", (item) -> {
                    editor.cancel();
                    ConfirmDialog dialog = new ConfirmDialog();
                    dialog.setHeader("Изменение");
                    dialog.setHeader("Применить изменения?");
                    dialog.setCancelable(true);
                    dialog.setCancelText("Нет");
                    dialog.setConfirmText("Да");
                    dialog.setConfirmText("Да");
                    dialog.addConfirmListener(event -> {
                        moneyOperationController.updateChecked(item.getId(), item.getRegularity());
                        grid.initGrid(plusMonth, h3);
                    });
                    dialog.addCancelListener(event -> {
                        grid.initGrid(plusMonth, h3);
                    });
                    dialog.open();
                });
    }
    private VerticalLayout createDialogLayout() {

        //общие поля
        SpecialTextField category = new SpecialTextField("Категория", "category");
        Select<String> regularity = new SpecialSelect<String>("regularity");
        regularity.setLabel("Тип платежа");
        regularity.setItems("Одиночный", "Регулярный");

        //single
        Locale russianLocale = new Locale("ru", "RU");
        SpecialDatePicker datePicker = new SpecialDatePicker("Дата", "date");
        datePicker.setLocale(russianLocale);
        SpecialNumberField money = new SpecialNumberField("Сумма", "money");
        datePicker.setVisible(false);
        money.setVisible(false);

        //regular
        Select<String> periodOfRegularity = new SpecialSelect<String>("periodOfRegularity");
        periodOfRegularity.setLabel("Тип регулярной оплаты");
        periodOfRegularity.setItems("Раз в месяц");
        Select<String> datesOfRegularity = new SpecialSelect<String>("datesOfRegularity");
        datesOfRegularity.setLabel("Дата регулярной оплаты");
        datesOfRegularity.setItems("Определена");

        SpecialNumberField payday = new SpecialNumberField("Стандартное число оплаты", "payday");

        List<Integer> selectableYears = List.of(LocalDate.now().getYear(), LocalDate.now().getYear() + 1);
        var yearPicker = new SpecialComboBox<>("Год первого платежа", selectableYears, "yearPicker");
        var monthPicker = new SpecialComboBox<>("Месяц первого платежа", "monthPicker", Month.values());
        monthPicker.setItemLabelGenerator(
                m -> m.getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")));
        SpecialNumberField standardRegularMoney = new SpecialNumberField("Планируемый расход", "standartMoney");
        periodOfRegularity.setVisible(false);
        datesOfRegularity.setVisible(false);
        payday.setVisible(false);
        yearPicker.setVisible(false);
        monthPicker.setVisible(false);
        standardRegularMoney.setVisible(false);

        //typeListener
        regularity.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Select<String>, String>>)
                selectStringComponentValueChangeEvent -> {
            if (regularity.getValue().equals("Одиночный")) {
                datePicker.setVisible(true);
                money.setVisible(true);
                periodOfRegularity.setVisible(false);
                datesOfRegularity.setVisible(false);
                payday.setVisible(false);
                yearPicker.setVisible(false);
                monthPicker.setVisible(false);
                standardRegularMoney.setVisible(false);
            } else {
                datePicker.setVisible(false);
                money.setVisible(false);
                periodOfRegularity.setVisible(true);
                datesOfRegularity.setVisible(true);
                payday.setVisible(true);
                yearPicker.setVisible(true);
                monthPicker.setVisible(true);
                standardRegularMoney.setVisible(true);
            }
        });

        datesOfRegularity.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Select<String>, String>>)
                selectStringComponentValueChangeEvent -> {

        });

        yearPicker.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<Integer>, Integer>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<ComboBox<Integer>, Integer> comboBoxIntegerComponentValueChangeEvent) {
                if (yearPicker.getValue() == LocalDateTime.now().getYear()) {
                    int curMon = LocalDate.now().getMonthValue();
                    monthPicker.setItems(Arrays.stream(Month.values()).filter(e -> e.getValue() >= curMon).toList());
                } else {
                    monthPicker.setItems(Month.values());
                }
            }
        });


        VerticalLayout dialogLayout = new VerticalLayout(category, regularity, datePicker, money, periodOfRegularity, datesOfRegularity, payday, yearPicker, monthPicker, standardRegularMoney);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }
    public class SpecialGrid<T extends MoneyOperationDTO> extends Grid<T> {

        public SpecialGrid(Class beanType, boolean autoCreateColumns) {
            super(beanType, autoCreateColumns);
        }

        public void initGrid(int diffMonth, SpecialH3 h3) {
            this.removeAllColumns();

            Editor<T> editor = this.getEditor();


            this.addColumn((Renderer<T>) createEmployeeRenderer(this, diffMonth, h3, editor)).setHeader("Выполнено");
            Grid.Column<MoneyOperationDTO> dateColumn = (Column<MoneyOperationDTO>) this.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
            Grid.Column<MoneyOperationDTO> categoryColumn = (Column<MoneyOperationDTO>) this.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
            Grid.Column<MoneyOperationDTO> moneyColumn = (Column<MoneyOperationDTO>) this.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");
            Grid.Column<MoneyOperationDTO> typeColumn = (Column<MoneyOperationDTO>) this.addColumn(MoneyOperationDTO::getTranslatedRegularity).setHeader("Тип");


            Grid.Column<MoneyOperationDTO> addEditColumn = (Column<MoneyOperationDTO>) this
                    .addComponentColumn(person -> {
                        Button editButton = new Button("✏️");
                        editButton.addClickListener(e -> {
                            if (editor.isOpen())
                                editor.cancel();
                            this.getEditor().editItem(person);
                        });
                        return editButton;
                    })
                    .setHeader(new Button("➕", buttonClickEvent ->
            {

                Dialog addLayoDialog = new Dialog();
                addLayoDialog.setHeaderTitle("Добавить обязательный платеж");
                VerticalLayout dialogLayout = createDialogLayout();
                addLayoDialog.add(dialogLayout);
                Button saveButton = new Button("Add", e -> {
                    var date = moneyOperationController.saveExpense(dialogLayout.getChildren().map(g -> ((Mappable) g).getDtoParameters()).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));

                    var firstDay = LocalDateTime.now().minusMonths(1).withDayOfMonth(1).minusDays(1).toLocalDate();
                    var lastDay = LocalDateTime.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).toLocalDate();

                    int diffMonthMe=0;

                    if (date.isAfter(firstDay) && date.isBefore(lastDay)) {
                        h3.setText(date.getMonth());
                        diffMonthMe = date.getMonthValue() - LocalDateTime.now().getMonthValue();
                    } else {
                        h3.setText(LocalDateTime.now().getMonth());
                    }
                    this.initGrid(diffMonthMe, h3);
                    addLayoDialog.close();
                });
                Button cancelButton = new Button("Cancel", e -> addLayoDialog.close());
                addLayoDialog.getFooter().add(cancelButton);
                addLayoDialog.getFooter().add(saveButton);
                addLayoDialog.open();
            }));


            Binder<MoneyOperationDTO> binder = new Binder<>();
            editor.setBinder((Binder<T>) binder);
            editor.setBuffered(true);

            TextField category = new TextField();
            DatePicker date = new DatePicker();
            NumberField money = new NumberField();

            binder.forField(date)
                    .asRequired("Date must not be empty")
                    .bind(MoneyOperationDTO::getDate, MoneyOperationDTO::setDate);
            dateColumn.setEditorComponent(date);

            binder.forField(money)
                    .asRequired("Money must not be empty")
                    .bind(MoneyOperationDTO::getRawMoney, MoneyOperationDTO::setMoney);
            moneyColumn.setEditorComponent(money);

            Button saveButton = new Button("\uD83D\uDCBE", e -> {

                if (editor.getItem().getRegularity() == OperationRegularity.SINGLE) {

                    binder.forField(category)
                            .asRequired("Category must not be empty")
                            .bind(MoneyOperationDTO::getCategory, MoneyOperationDTO::setCategory);
                    categoryColumn.setEditorComponent(category);

                    moneyOperationController.updateSingle(
                            editor.getItem().getId(),
                            category.getValue(),
                            date.getValue(),
                            money.getValue()
                    );
                    editor.save();
                } else {
                    moneyOperationController.updateRegularUnit(
                            editor.getItem().getId(),
                            date.getValue(),
                            money.getValue()
                    );
                }
                initGrid(diffMonth, h3);
            });
            Button cancelButton = new Button("Cancel",
                    e -> editor.cancel());
            Button deleteButton = new Button("Delete", e -> {
                moneyOperationController.deleteExpense(editor.getItem().getId(), editor.getItem().getRegularity());
                editor.cancel();
                initGrid(diffMonth, h3);
            });

            HorizontalLayout actions = new HorizontalLayout(
                    saveButton,
                    cancelButton,
                    deleteButton
            );
            actions.setPadding(false);
            addEditColumn.setEditorComponent(actions);

            this.setItems((Collection<T>) moneyOperationController.getExpenses(LocalDateTime.now().plusMonths(diffMonth).getYear(), LocalDateTime.now().plusMonths(diffMonth).getMonth().getValue()));
        }
    }
    public class SpecialComboBox<T> extends ComboBox<T> implements Mappable {

        private String hiddenLabel;

        public SpecialComboBox(String label, Collection<T> items, String hiddenLabel) {
            super(label, items);
            this.hiddenLabel = hiddenLabel;
        }

        public SpecialComboBox(String label, String hiddenLabel, T... items) {
            super(label, items);
            this.hiddenLabel = hiddenLabel;
        }

        @Override
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters() {
            return new AbstractMap.SimpleEntry<String, Object>(hiddenLabel, super.getValue() != null ? super.getValue() : new Object());
        }

        @Override
        public T getValue() {
            return super.getValue();
        }
    }
    public class SpecialTextField extends TextField implements Mappable {
        private String hiddenLabel;
        public SpecialTextField(String label, String hiddenLabel) {
            super(label);
            this.hiddenLabel = hiddenLabel;
        }

        @Override
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters() {
            return new AbstractMap.SimpleEntry<String, Object>(hiddenLabel, super.getValue() != null ? super.getValue() : new Object());
        }
    }
    public class SpecialSelect<T> extends Select implements Mappable {

        private String hiddenLabel;

        public SpecialSelect(String hiddenLabel) {
            super();
            this.hiddenLabel = hiddenLabel;
        }

        @Override
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters() {
            return new AbstractMap.SimpleEntry<String, Object>(hiddenLabel, super.getValue() != null ? super.getValue() : new Object());
        }

        @Override
        public Registration addValueChangeListener(ValueChangeListener valueChangeListener) {
            return super.addValueChangeListener(valueChangeListener);
        }

        @Override
        public SelectDataView setItems(DataProvider dataProvider) {
            return super.setItems(dataProvider);
        }
    }
    public class SpecialDatePicker extends DatePicker implements Mappable {

        private String hiddenLabel;

        public SpecialDatePicker(String label, String hiddenLabel) {
            super(label);
            this.hiddenLabel = hiddenLabel;
        }

        @Override
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters() {
            return new AbstractMap.SimpleEntry<String, Object>(hiddenLabel, super.getValue() != null ? super.getValue() : new Object());
        }
    }
    public class SpecialNumberField extends NumberField implements Mappable {
        private String hiddenLabel;
        public SpecialNumberField(String label, String hiddenLabel) {
            super(label);
            this.hiddenLabel = hiddenLabel;
        }

        @Override
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters() {
            return new AbstractMap.SimpleEntry<String, Object>(hiddenLabel, super.getValue() != null ? super.getValue().floatValue() : new Object());
        }
    }
    public class SpecialH3 extends H3 {
        private Month month;
        public SpecialH3(Month month) {
            this.setText(month);
            this.month = month;
        }

        public void setText(Month month) {
            String initText = "Обязательные платежи на ";
            this.setText(initText + month.getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")) + ": (клик для смены месяца)");
        }

        public Month getMonth() {
            return month;
        }
    }
    public interface Mappable {
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters();
    }
}
