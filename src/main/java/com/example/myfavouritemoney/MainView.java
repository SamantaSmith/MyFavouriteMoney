package com.example.myfavouritemoney;

import com.example.myfavouritemoney.controller.MoneyOperationController;
import com.example.myfavouritemoney.controller.MonitoredCategoryController;
import com.example.myfavouritemoney.controller.WalletController;
import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.dto.MonitoredCategoryDTO;
import com.example.myfavouritemoney.dto.WalletDTO;
import com.example.myfavouritemoney.enums.OperationRegularity;
import com.example.myfavouritemoney.enums.OperationType;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.select.data.SelectDataView;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;
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
@PermitAll
@PreserveOnRefresh
public class MainView extends VerticalLayout {

    @Autowired
    private WalletController walletController;
    @Autowired
    private MoneyOperationController moneyOperationController;
    @Autowired
    private MonitoredCategoryController monitoredCategoryController;

    public MainView( WalletController walletController,
                     MoneyOperationController moneyOperationController,
                     MonitoredCategoryController monitoredCategoryController) {
        this.walletController = walletController;
        this.moneyOperationController = moneyOperationController;
        this.monitoredCategoryController = monitoredCategoryController;

        //Мои кошельки
        VerticalLayout walletsList = new VerticalLayout();
        H3 walletsH3 = new H3("Мои кошельки");
        walletsList.add(walletsH3);
        walletsH3.getStyle().set("margin-bottom", "20px");
        walletsList.setAlignSelf(Alignment.CENTER, walletsH3);



        Grid<WalletDTO> walletGrid = new Grid<>(WalletDTO.class, false);
        walletsList.add(walletGrid);


        //Расходы за месяц
        SpecialMoneyOperationDTOGrid<MoneyOperationDTO> moneyOperationExpenseDTOGrid = new SpecialMoneyOperationDTOGrid<>(MoneyOperationDTO.class, false, OperationType.EXPENSE);
        var expenseH3 = new SpecialH3(LocalDateTime.now().getMonth(), moneyOperationExpenseDTOGrid, "Обязательные платежи на ");
        VerticalLayout expensesList = new VerticalLayout();
        expensesList.add(expenseH3);
        expensesList.setAlignSelf(Alignment.CENTER, expenseH3);
        expenseH3.getStyle().set("margin-bottom", "20px");
        moneyOperationExpenseDTOGrid.initGrid(0, expenseH3);

        expensesList.add(moneyOperationExpenseDTOGrid);


        //Контролируемые категории
        SpecialMonitoredCategoryDTOGrid<MonitoredCategoryDTO> monitoredCategoriesGrid = new SpecialMonitoredCategoryDTOGrid<>(MonitoredCategoryDTO.class, false);
        VerticalLayout monitoredCategoriesList = new VerticalLayout();
        var controlCatH3 = new SpecialH3(LocalDate.now().getMonth(), monitoredCategoriesGrid, "Контролируемые категории на ");
        monitoredCategoriesList.add(controlCatH3);
        monitoredCategoriesList.setAlignSelf(Alignment.CENTER, controlCatH3);
        controlCatH3.getStyle().set("margin-bottom", "20px");
        monitoredCategoriesGrid.initGrid(0, controlCatH3);

        monitoredCategoriesList.add(monitoredCategoriesGrid);


        //Доходы за месяц
        SpecialMoneyOperationDTOGrid<MoneyOperationDTO> moneyOperationIncomeDTOGrid = new SpecialMoneyOperationDTOGrid<>(MoneyOperationDTO.class, false, OperationType.INCOME);
        VerticalLayout incomeList = new VerticalLayout();
        var incomeH3 = new SpecialH3(LocalDateTime.now().getMonth(), moneyOperationIncomeDTOGrid, "Планируемые доходы на ");
        incomeList.add(incomeH3);
        incomeList.setAlignSelf(Alignment.CENTER, incomeH3);
        incomeH3.getStyle().set("margin-bottom", "20px");
        moneyOperationIncomeDTOGrid.initGrid(0, incomeH3);

        incomeList.add(moneyOperationIncomeDTOGrid);


        //Дашборд
        VerticalLayout dashboardLayout = new VerticalLayout();
        var dashH3 = new H3("Дашборд");
        dashH3.getStyle().set("font-size", "20px");
        dashboardLayout.add(dashH3);
        dashboardLayout.setWidth("700px");
        //dashboardLayout.setHeight("1000px");
        dashboardLayout.setAlignSelf(Alignment.CENTER, dashH3);

        dashboardLayout.add(initFormDashboardCards(dashboardLayout));
        setDashboardStyle(dashboardLayout.getStyle());
        dashboardLayout.setMargin(true);
        dashboardLayout.setPadding(true);


        //Общее
        HorizontalLayout mainLayo = new HorizontalLayout();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addAndExpand(walletsList);
        verticalLayout.addAndExpand(monitoredCategoriesList);

        VerticalLayout verticalLayout2 = new VerticalLayout();
        verticalLayout2.addAndExpand(expensesList);
        verticalLayout2.addAndExpand(incomeList);

        mainLayo.addAndExpand(verticalLayout);
        mainLayo.addAndExpand(verticalLayout2);
        mainLayo.add(dashboardLayout);
        mainLayo.getStyle().setBackground("SeaShell");
        mainLayo.setPadding(true);
        mainLayo.getStyle().setBorder("2px solid black");
        mainLayo.getStyle().set("border-radius", "10px");

        H1 headerH1 = new H1("My Favourite Money");
        headerH1.getStyle().set("margin-bottom", "40px");
        headerH1.getStyle().set("margin-top", "40px");

        add(
                headerH1,
                mainLayo
        );

        this.setAlignSelf(Alignment.CENTER, headerH1);
    }

    private Collection<com.vaadin.flow.component.Component> initFormDashboardCards(VerticalLayout baseLayo) {

        Collection<com.vaadin.flow.component.Component> collection = new ArrayList<>();

        //Кошельки
        if (walletController.getWallets().size() == 0) {
            var layout = new SimpleDashboardCard("У вас не заполнен раздел кошельков. Заполните его", baseLayo);
            collection.add(layout);
        }

        if (!walletController.isActualizedWallets()) {
            var layout = new SimpleDashboardCard("Данные по кошелькам не актуализированы сегодня. Обновите их", baseLayo);
            collection.add(layout);
        }


        return collection;
    }
    private Renderer<MoneyOperationDTO> createEmployeeRenderer(SpecialMoneyOperationDTOGrid grid, int plusMonth, SpecialH3 h3, Editor editor) {
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
    private VerticalLayout createMoneyOperationDialogLayout() {

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
            } else
            {
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
    private VerticalLayout createMonitoredCategoryDialogLayout() {
        SpecialTextField category = new SpecialTextField("Категория", "category");
        List<Integer> selectableYears = List.of(LocalDate.now().getYear(), LocalDate.now().getYear() + 1);
        var yearPicker = new SpecialComboBox<>("Год отсчета", selectableYears, "yearPicker");
        var monthPicker = new SpecialComboBox<>("Месяц отсчета", "monthPicker", Month.values());
        monthPicker.setItemLabelGenerator(
                m -> m.getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")));
        SpecialNumberField money = new SpecialNumberField("Лимит трат на месяц", "limitMoney");

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

        VerticalLayout dialogLayout = new VerticalLayout(category, yearPicker, monthPicker, money);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private VerticalLayout createWalletDialogLayout() {
        List<String> selectableWalletTypes = List.of("Наличные", "")
    }

    public class SimpleDashboardCard extends VerticalLayout {
        public SimpleDashboardCard(String text, VerticalLayout layout) {
            super();
            this.setPadding(true);
            setDashboardStyle(this.getStyle());
            var h3 = new H3(text);
            h3.getStyle().set("font-size", "16px");
            this.add(h3);
            var complete = new Checkbox("Выполнено");
            complete.addValueChangeListener(new HasValue.ValueChangeListener<>() {
                @Override
                public void valueChanged(AbstractField.ComponentValueChangeEvent<Checkbox, Boolean> checkboxBooleanComponentValueChangeEvent) {
                    ConfirmDialog dialog = new ConfirmDialog();
                    dialog.setHeader("Изменение");
                    dialog.setHeader("Применить изменения?");
                    dialog.setCancelable(true);
                    dialog.setCancelText("Нет");
                    dialog.setConfirmText("Да");
                    dialog.addConfirmListener(event -> {
                        layout.remove(SimpleDashboardCard.this);
                        dialog.close();
                    });
                    dialog.addCancelListener(event -> {
                        complete.setValue(!complete.getValue());
                        dialog.close();
                    });
                    dialog.open();
                }
            });
            this.add(complete);
        }
    }
    private void setDashboardStyle(Style style) {
        style.setBackground("BlanchedAlmond");
        style.setBorder("2px solid black");
        style.set("border-radius", "10px");
    }
    public class SpecialWalletsDTOGrid<T extends WalletDTO> extends Grid<T> implements InitableGrid {

        public SpecialWalletsDTOGrid(Class beanType, boolean autoCreateColumns) {
            super(beanType, autoCreateColumns);
        }

        @Override
        public void initGrid(int diffMonth, SpecialH3 h3) {
            this.removeAllColumns();
            Editor<T> editor = this.getEditor();

            var wallets = walletController.getWallets();
            var sum = (Double) wallets.stream().map(WalletDTO::getMoney).mapToDouble(Float::doubleValue).sum();
            this.addColumn(WalletDTO::getName).setHeader("Имя кошелька");
            this.addColumn(WalletDTO::getType).setHeader("Тип");
            this.addColumn(WalletDTO::getMoney).setHeader("Сумма").setFooter(sum + "₽");

            Grid.Column<WalletDTO> addEditColumn = (Column<WalletDTO>) this.addComponentColumn(wallet -> {
                Button editButton = new Button("✏️");
                editButton.addClickListener(e -> {
                    if (editor.isOpen())
                        editor.cancel();
                    this.getEditor().editItem(wallet);
                });
                return editButton;
            }).setHeader(new Button("➕", buttonClickEvent -> {

                Dialog addLayoDialog = new Dialog();
                addLayoDialog.setHeaderTitle("Добавить новый кошелек");
                VerticalLayout dialogLayout = createMoneyOperationDialogLayout();
                addLayoDialog.add(dialogLayout);
                Button saveButton = new Button("Add", e -> {
                    var date = moneyOperationController.saveExpense(
                            dialogLayout.getChildren().map(g -> ((Mappable) g).getDtoParameters()).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)),
                            operationType
                    );
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

            this.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            this.setItems((Collection<T>)wallets);

        }
    }
    public class SpecialMoneyOperationDTOGrid<T extends MoneyOperationDTO> extends Grid<T> implements InitableGrid {
        private OperationType operationType;

        public SpecialMoneyOperationDTOGrid(Class beanType, boolean autoCreateColumns, OperationType operationType) {
            super(beanType, autoCreateColumns);
            this.operationType = operationType;
        }

        @Override
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
                VerticalLayout dialogLayout = createMoneyOperationDialogLayout();
                addLayoDialog.add(dialogLayout);
                Button saveButton = new Button("Add", e -> {
                    var date = moneyOperationController.saveExpense(
                            dialogLayout.getChildren().map(g -> ((Mappable) g).getDtoParameters()).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)),
                            operationType
                    );
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

            this.setItems((Collection<T>) moneyOperationController.getOperations(
                    LocalDateTime.now().plusMonths(diffMonth).getYear(),
                    LocalDateTime.now().plusMonths(diffMonth).getMonth().getValue(),
                    operationType)
            );
            this.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        }
    }
    public class SpecialMonitoredCategoryDTOGrid<T extends MonitoredCategoryDTO> extends Grid<T> implements InitableGrid {

        public SpecialMonitoredCategoryDTOGrid(Class beanType, boolean autoCreateColumns) {
            super(beanType, autoCreateColumns);
        }

        @Override
        public void initGrid(int diffMonth, SpecialH3 h3) {
            this.removeAllColumns();
            var monitoredCategories = monitoredCategoryController.getCategories(LocalDateTime.now().plusMonths(diffMonth).getYear(), LocalDateTime.now().plusMonths(diffMonth).getMonth().getValue());
            this.addColumn(MonitoredCategoryDTO::getName).setHeader("Имя");
            this.addColumn(MonitoredCategoryDTO::getMonthLimitString).setHeader("Лимит на месяц");
            this.addComponentColumn(t -> {
                var floatProgress = t.getCurrentExpense()/t.getMonthLimit();
                var progress = floatProgress < 1 ? t.getCurrentExpense()/t.getMonthLimit() : 1;

                ProgressBar progressBar = new ProgressBar();

                if (floatProgress < 0.5) {
                    progressBar.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
                } else if (floatProgress > 0.5 && floatProgress < 0.8) {
                    progressBar.addThemeVariants(ProgressBarVariant.LUMO_CONTRAST);
                } else {
                    progressBar.addThemeVariants(ProgressBarVariant.LUMO_ERROR);
                }

                progressBar.setValue(progress);

                NativeLabel progressBarLabelText = new NativeLabel(String.valueOf(t.getCurrentExpenseString()));
                progressBarLabelText.setId("label");
                progressBar.getElement().setAttribute("aria-labelledby", "label");
                NativeLabel progressBarLabelValue = new NativeLabel(String.format(Locale.US, "%d%%", (int) (floatProgress * 100)));
                HorizontalLayout hl1 = new HorizontalLayout(progressBarLabelText);
                HorizontalLayout hl2 = new HorizontalLayout(progressBarLabelValue);
                HorizontalLayout hl = new HorizontalLayout();
                hl.addAndExpand(hl1, hl2);

                VerticalLayout vLayo = new VerticalLayout();
                vLayo.add(hl, progressBar);
                return vLayo;
            }).setHeader("Прогресс бар расходов").setFlexGrow(2);
            this.addComponentColumn(p -> {
                        Button addExpenseButton = new Button("\uD83D\uDCB5");
                        Button sendExpense = new Button("➡️");
                        Button cancelButton = new Button("Cancel");

                        NumberField numberField = new NumberField();
                        numberField.setWidth("80px");
                        numberField.setVisible(false);
                        sendExpense.setVisible(false);
                        cancelButton.setVisible(false);

                        addExpenseButton.addClickListener(e -> {
                            addExpenseButton.setVisible(false);
                            numberField.setVisible(true);
                            sendExpense.setVisible(true);
                            cancelButton.setVisible(true);
                        });

                        cancelButton.addClickListener(e -> {
                            addExpenseButton.setVisible(true);
                            numberField.setVisible(false);
                            sendExpense.setVisible(false);
                            cancelButton.setVisible(false);
                        });

                        sendExpense.addClickListener(e -> {
                            monitoredCategoryController.addExpense(p.getId(), numberField.getValue().floatValue());
                            this.initGrid(diffMonth, h3);
                            addExpenseButton.setVisible(true);
                            numberField.setVisible(false);
                            sendExpense.setVisible(false);
                            cancelButton.setVisible(false);
                        });

                        HorizontalLayout layout = new HorizontalLayout();
                        layout.add(addExpenseButton, numberField, sendExpense, cancelButton);
                        return layout;
                    })
                    .setHeader(new Button("➕", buttonClickEvent ->
                    {
                        Dialog addLayoDialog = new Dialog();
                        addLayoDialog.setHeaderTitle("Добавить контролируемую категорию");
                        VerticalLayout dialogLayout = createMonitoredCategoryDialogLayout();
                        addLayoDialog.add(dialogLayout);

                        Button saveButton = new Button("Add", e -> {
                            var date = monitoredCategoryController.saveCategory(dialogLayout.getChildren().map(g -> ((Mappable) g).getDtoParameters()).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
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

            this.setItems((Collection<T>) monitoredCategories);
            this.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
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
        private String initText;
        public SpecialH3(Month month, InitableGrid grid, String initText) {
            this.month = month;
            this.initText = initText;
            this.setText(month);
            this.addClickListener((ComponentEventListener<ClickEvent<H3>>) h3ClickEvent -> {

                Dialog dialog = new Dialog();
                dialog.setHeaderTitle("Выбор месяца");

                Button previousMonthButton = new Button(LocalDateTime.now().minusMonths(1).getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")), e -> {
                    this.setText(LocalDateTime.now().minusMonths(1).getMonth());
                    grid.initGrid(-1, this);
                    dialog.close();
                });
                Button currentMonthButton = new Button(LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")), e -> {
                    this.setText(LocalDateTime.now().getMonth());
                    grid.initGrid(0, this);
                    dialog.close();
                });
                Button upComingMonthButton = new Button(LocalDateTime.now().plusMonths(1).getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")), e -> {
                    this.setText(LocalDateTime.now().plusMonths(1).getMonth());
                    grid.initGrid(1, this);
                    dialog.close();
                });

                dialog.getFooter().add(previousMonthButton);
                dialog.getFooter().add(currentMonthButton);
                dialog.getFooter().add(upComingMonthButton);
                dialog.open();
            });
        }

        public void setText(Month month) {
            String initText = this.initText;
            this.setText(initText + month.getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")));
            this.getStyle().set("font-size", "20px");
        }
        public Month getMonth() {
            return month;
        }
    }
    public interface Mappable {
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters();
    }
    public interface InitableGrid {
        public void initGrid(int diffMonth, SpecialH3 h3);
    }
}
