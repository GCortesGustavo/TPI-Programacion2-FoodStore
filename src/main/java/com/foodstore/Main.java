/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.foodstore;

import config.ConexionDB;

/**
 *
 * @author Usuario
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        try{
            ConexionDB.getConnection();
        } catch (Exception error) {
            System.out.println("Error" + error.getMessage());
        }
    }
}
