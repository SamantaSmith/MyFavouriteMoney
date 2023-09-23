package com.example.myfavouritemoney;

import com.example.myfavouritemoney.controller.WalletController;
import com.example.myfavouritemoney.dto.WalletDTO;
import com.example.myfavouritemoney.entities.Wallet;
import com.example.myfavouritemoney.service.WalletService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Route(value = "")
@Component
public class MainView extends VerticalLayout {

    private WalletController walletController;

    @Autowired
    public MainView( WalletController walletController) {
        this.walletController = walletController;

        VerticalLayout walletsList = new VerticalLayout();
        walletsList.setWidth("25%");


        Grid<WalletDTO> walletGrid = new Grid<>(WalletDTO.class, false);
        walletGrid.addColumn(WalletDTO::getName).setHeader("Имя кошелька");
        walletGrid.addColumn(WalletDTO::getType).setHeader("Тип");
        walletGrid.addColumn(WalletDTO::getMoney).setHeader("Сумма");
        walletGrid.setItems(walletController.getWallets());
        walletsList.add(walletGrid);

        add(
                new H1("My Favourite Money"),
                new H3("Мои кошельки:"),
                walletsList
        );
    }
}
