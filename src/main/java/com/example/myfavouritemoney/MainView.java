package com.example.myfavouritemoney;

import com.example.myfavouritemoney.controller.MoneyOperationController;
import com.example.myfavouritemoney.controller.WalletController;
import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.dto.WalletDTO;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Route(value = "")
@Component
@PreserveOnRefresh
public class MainView extends VerticalLayout {

    private WalletController walletController;
    private MoneyOperationController moneyOperationController;

    @Autowired
    public MainView( WalletController walletController, MoneyOperationController moneyOperationController) {
        this.walletController = walletController;
        this.moneyOperationController = moneyOperationController;

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


        VerticalLayout expensesList = new VerticalLayout();
        expensesList.add(new H3(String.format("Мои расходы за %s:", LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")))));

        Grid<MoneyOperationDTO> moneyOperationDTOGrid = new Grid<>(MoneyOperationDTO.class, false);

        moneyOperationDTOGrid.addColumn(createEmployeeRenderer()).setHeader("Выполнено");
        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");
        moneyOperationDTOGrid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue()));
        moneyOperationDTOGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        expensesList.add(moneyOperationDTOGrid);


        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addAndExpand(walletsList);
        horizontalLayout.addAndExpand(expensesList);

        add(
                new H1("My Favourite Money"),
                horizontalLayout
        );
    }

    private Renderer<MoneyOperationDTO> createEmployeeRenderer() {
        return LitRenderer.<MoneyOperationDTO> of(
                        "<vaadin-checkbox @click=\"${handleCheckClick}\" ?checked=\"${item.checked}\"></vaadin-checkbox>")
                .withProperty("checked", MoneyOperationDTO::getCompleted)
                .withFunction("handleCheckClick", (item) -> {
                    moneyOperationController.updateChecked(item.getId());
                });
    }
}
