package com.example.myfavouritemoney;

import com.example.myfavouritemoney.controller.MoneyOperationController;
import com.example.myfavouritemoney.controller.WalletController;
import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.dto.WalletDTO;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

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

        moneyOperationDTOGrid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue()));
        moneyOperationDTOGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
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
                        grid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().getYear(), LocalDateTime.now().plusMonths(plusMonth).getMonth().getValue()));
                    });
                    dialog.open();
                });
    }
}
