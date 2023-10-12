package com.example.myfavouritemoney;

import com.example.myfavouritemoney.controller.MoneyOperationController;
import com.example.myfavouritemoney.controller.WalletController;
import com.example.myfavouritemoney.dto.MoneyOperationDTO;
import com.example.myfavouritemoney.dto.WalletDTO;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PreserveOnRefresh;
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
    public List<GridSortOrder<MoneyOperationDTO>> listO;

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

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Изменение состояния");
        dialog.setHeader("Вы действительно хоитте изменить состояние?");
        dialog.setCancelable(true);
        dialog.setCancelText("Нет");
        dialog.setConfirmText("Да");

        VerticalLayout expensesList = new VerticalLayout();
        expensesList.add(new H3(String.format("Мои расходы за %s:", LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru")))));

        Grid<MoneyOperationDTO> moneyOperationDTOGrid = new Grid<>(MoneyOperationDTO.class, false);

        Grid.Column<MoneyOperationDTO> checked = moneyOperationDTOGrid
                .addColumn(createEmployeeRenderer())
                .setHeader("Выполнено")
                //.setComparator(MoneyOperationDTO::getCompletedBoolean)
                ;
        Grid.Column<MoneyOperationDTO> date = moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
        moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");

        moneyOperationDTOGrid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue()));
        moneyOperationDTOGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        //moneyOperationDTOGrid.setMultiSort(true, Grid.MultiSortPriority.PREPEND);
        //var sortOrderList = new ArrayList<GridSortOrder<MoneyOperationDTO>>();
        //sortOrderList.add(new GridSortOrder<MoneyOperationDTO>(checked, SortDirection.ASCENDING));
        //sortOrderList.add(new GridSortOrder<MoneyOperationDTO>(date, SortDirection.ASCENDING));
        //moneyOperationDTOGrid.sort(sortOrderList.stream().toList());
        expensesList.add(moneyOperationDTOGrid);

        dialog.setConfirmText("Да");
        //dialog.addConfirmListener(event)

        moneyOperationDTOGrid.addItemClickListener(new ComponentEventListener<ItemClickEvent<MoneyOperationDTO>>() {
            @Override
            public void onComponentEvent(ItemClickEvent<MoneyOperationDTO> moneyOperationDTOItemClickEvent) {
                System.out.println("logmenow");
                moneyOperationDTOGrid.removeAllColumns();
                System.out.println(moneyOperationDTOGrid.getColumns());

                Grid.Column<MoneyOperationDTO> checked = moneyOperationDTOGrid
                        .addColumn(createEmployeeRenderer())
                        .setHeader("Выполнено")
                        //.setComparator(MoneyOperationDTO::getCompletedBoolean)
                        ;
                Grid.Column<MoneyOperationDTO> date = moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getDate).setHeader("Дата");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getCategory).setHeader("Категория");
                moneyOperationDTOGrid.addColumn(MoneyOperationDTO::getMoney).setHeader("Расход");

                moneyOperationDTOGrid.setItems(moneyOperationController.getExpenses(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue()));
                //var sortOrderList = new ArrayList<GridSortOrder<MoneyOperationDTO>>();
                //sortOrderList.add(new GridSortOrder<MoneyOperationDTO>(checked, SortDirection.ASCENDING));
                //sortOrderList.add(new GridSortOrder<MoneyOperationDTO>(date, SortDirection.ASCENDING));
                //moneyOperationDTOGrid.sort(sortOrderList.stream().toList());
                System.out.println(moneyOperationDTOGrid.getSelectedItems());
            }
        });

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
