package com.dsi.controller;

import com.dsi.model.bll.BLLException;

public class Main {
    public static void main(String[] args) throws BLLException {
        BOConnexion boc = new BOConnexion();
        boc.actionIdentification("admin", "P@ssw0rd");

    }
}
