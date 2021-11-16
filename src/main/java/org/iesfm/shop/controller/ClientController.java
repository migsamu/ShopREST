package org.iesfm.shop.controller;

import org.iesfm.shop.Client;
import org.iesfm.shop.dao.ClientDAO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    private ClientDAO clientDAO;

    public ClientController(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/clients")
    public List<Client> list() {
        return clientDAO.list();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/clients")
    public void insert(@RequestBody Client client) {
        clientDAO.insert(client);
    }

}
