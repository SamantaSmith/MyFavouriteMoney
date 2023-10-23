package com.example.myfavouritemoney;

import com.example.myfavouritemoney.controller.MoneyOperationController;
import com.example.myfavouritemoney.controller.WalletController;
import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.dto.WalletDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.select.data.SelectDataView;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Route(value = "")
@Component
public class MainView extends VerticalLayout {

    private WalletController walletController;
    private MoneyOperationController moneyOperationController;

    @Autowired
    public MainView( WalletController walletController, MoneyOperationController moneyOperationController) {
        this.walletController = walletController;
        this.moneyOperationController = moneyOperationController;

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

        var h3 = new H3
                (String.format("Обязательные платежи на %s",
                        LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")))+": (клик для смены месяца)");


        VerticalLayout expensesList = new VerticalLayout();
        expensesList.add(h3);

        Grid<MoneyOperationDTO> moneyOperationDTOGrid = new Grid<>(MoneyOperationDTO.class, false);

        moneyOperationDTOGrid.addColumn(createEmployeeRenderer(moneyOperationDTOGrid, 0)).setHeader("Выполнено");
        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");
        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getTranslatedRegularity).setHeader("Тип");

        moneyOperationDTOGrid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue()));
        moneyOperationDTOGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getMoney).setHeader(new Button(new Icon(VaadinIcon.PLUS), buttonClickEvent -> {

            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Добавить обязательный платеж");
            VerticalLayout dialogLayout = createDialogLayout();
            dialog.add(dialogLayout);
            Button saveButton = new Button("Add", e -> {
                moneyOperationController.saveSingleExpense(dialogLayout.getChildren().map(g -> ((Mappable) g).getDtoParameters()).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
                moneyOperationDTOGrid.removeAllColumns();
                moneyOperationDTOGrid.addColumn(createEmployeeRenderer(moneyOperationDTOGrid, 0)).setHeader("Выполнено");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getTranslatedRegularity).setHeader("Тип");
                moneyOperationDTOGrid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().minusMonths(0).getYear(), LocalDateTime.now().minusMonths(0).getMonth().getValue()));
                dialog.close();
            });
            Button cancelButton = new Button("Cancel", e -> dialog.close());
            dialog.getFooter().add(cancelButton);
            dialog.getFooter().add(saveButton);
            dialog.open();
        }));


        expensesList.add(moneyOperationDTOGrid);

        h3.addClickListener((ComponentEventListener<ClickEvent<H3>>) h3ClickEvent -> {

            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Выбор месяца");

            Button previousMonthButton = new Button(LocalDateTime.now().minusMonths(1).getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")), e -> {
                h3.setText(String.format("Обязательные платежи на %s",
                        LocalDateTime.now().minusMonths(1).getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"))) + ": (клик для смены месяца)");
                moneyOperationDTOGrid.removeAllColumns();
                moneyOperationDTOGrid.addColumn(createEmployeeRenderer(moneyOperationDTOGrid, -1)).setHeader("Выполнено");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getTranslatedRegularity).setHeader("Тип");
                moneyOperationDTOGrid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().minusMonths(1).getYear(), LocalDateTime.now().minusMonths(1).getMonth().getValue()));
                dialog.close();
            });
            Button currentMonthButton = new Button(LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")), e -> {
                h3.setText(String.format("Обязательные платежи на %s",
                        LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"))) + ": (клик для смены месяца)");
                moneyOperationDTOGrid.removeAllColumns();
                moneyOperationDTOGrid.addColumn(createEmployeeRenderer(moneyOperationDTOGrid, 0)).setHeader("Выполнено");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getTranslatedRegularity).setHeader("Тип");
                moneyOperationDTOGrid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue()));
                dialog.close();
            });
            Button upComingMonthButton = new Button(LocalDateTime.now().plusMonths(1).getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")), e -> {
                h3.setText(String.format("Обязательные платежи на %s",
                        LocalDateTime.now().plusMonths(1).getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"))) + ": (клик для смены месяца)");
                moneyOperationDTOGrid.removeAllColumns();
                moneyOperationDTOGrid.addColumn(createEmployeeRenderer(moneyOperationDTOGrid, 1)).setHeader("Выполнено");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getTranslatedRegularity).setHeader("Тип");
                moneyOperationDTOGrid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().plusMonths(1).getYear(), LocalDateTime.now().plusMonths(1).getMonth().getValue()));
                dialog.close();
            });

            dialog.getFooter().add(previousMonthButton);
            dialog.getFooter().add(currentMonthButton);
            dialog.getFooter().add(upComingMonthButton);
            dialog.open();
        });


        //Общее

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addAndExpand(walletsList);
        horizontalLayout.addAndExpand(expensesList);

        add(
                new H1("My Favourite Money"),
                horizontalLayout
        );
    }

    private Renderer<MoneyOperationDTO> createEmployeeRenderer(Grid<MoneyOperationDTO> grid, int plusMonth) {
        return LitRenderer.<MoneyOperationDTO> of(
                        "<vaadin-checkbox @click=\"${handleCheckClick}\" ?checked=\"${item.checked}\"></vaadin-checkbox>")
                .withProperty("checked", MoneyOperationDTO::getCompleted)
                .withFunction("handleCheckClick", (item) -> {

                    ConfirmDialog dialog = new ConfirmDialog();
                    dialog.setHeader("Изменение");
                    dialog.setHeader("Применить изменения?");
                    dialog.setCancelable(true);
                    dialog.setCancelText("Нет");
                    dialog.setConfirmText("Да");
                    dialog.setConfirmText("Да");
                    dialog.addConfirmListener(event -> {

                        moneyOperationController.updateChecked(item.getId(), item.getRegularity());

                        grid.removeAllColumns();
                        grid.addColumn(createEmployeeRenderer(grid, plusMonth)).setHeader("Выполнено");
                        grid.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
                        grid.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
                        grid.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");
                        grid.addColumn(MoneyOperationDTO::getTranslatedRegularity).setHeader("Тип");
                        grid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().getYear(), LocalDateTime.now().plusMonths(plusMonth).getMonth().getValue()));
                    });
                    dialog.open();
                });
    }

    private static VerticalLayout createDialogLayout() {

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

        //typeListener
        regularity.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<Select<String>, String>>)
                selectStringComponentValueChangeEvent -> {
            if (regularity.getValue().equals("Одиночный")) {
                datePicker.setVisible(true);
                money.setVisible(true);
            }
        });


        VerticalLayout dialogLayout = new VerticalLayout(category, regularity, datePicker, money);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    static class SpecialTextField extends TextField implements Mappable {
        private String hiddenLabel;
        public SpecialTextField(String label, String hiddenLabel) {
            super(label);
            this.hiddenLabel = hiddenLabel;
        }

        @Override
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters() {
            return new AbstractMap.SimpleEntry<String, Object>(hiddenLabel, super.getValue());
        }
    }
    static class SpecialSelect<T> extends Select implements Mappable {

        private String hiddenLabel;

        public SpecialSelect(String hiddenLabel) {
            super();
            this.hiddenLabel = hiddenLabel;
        }

        @Override
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters() {
            return new AbstractMap.SimpleEntry<String, Object>(hiddenLabel, super.getValue());
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
    static class SpecialDatePicker extends DatePicker implements Mappable {

        private String hiddenLabel;

        public SpecialDatePicker(String label, String hiddenLabel) {
            super(label);
            this.hiddenLabel = hiddenLabel;
        }

        @Override
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters() {
            return new AbstractMap.SimpleEntry<String, Object>(hiddenLabel, super.getValue());
        }
    }
    static class SpecialNumberField extends NumberField implements Mappable {
        private String hiddenLabel;
        public SpecialNumberField(String label, String hiddenLabel) {
            super(label);
            this.hiddenLabel = hiddenLabel;
        }

        @Override
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters() {
            return new AbstractMap.SimpleEntry<String, Object>(hiddenLabel, super.getValue().floatValue());
        }
    }

    //static class SpecialH3 extends H3 {
//
    //    public int compareMonthToCurrent() {
    //        if (super.getText().contains())
    //    }
    //}

    static interface Mappable {
        public AbstractMap.SimpleEntry<String, Object> getDtoParameters();
    }
}
