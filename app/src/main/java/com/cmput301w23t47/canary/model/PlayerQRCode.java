package com.cmput301w23t47.canary.model;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the QR Code scanned by the Player
 */
public class PlayerQRCode {
    // Player who scanned the QR
    private Player player;
    // QR Code scanned
    private QRCode qrCode;
    // Location of the QR
    private Location location;
    // name of the QR
    private String name;
    // Snapshot of the QR
    private Snapshot snapshot;
    Faker faker = new Faker();
    /**
     * Constructor for the randomGenName
     * @param: name
     * @return two combine gen name
     **/
    public String randomGenName(String name) {
        Random random = new Random();
        String genName="";
        for (int i=0;i<2;i++){
            genName+=produceName(random.nextInt(10));
        }
        return genName;
    }
    /**
        * Constructor for the produceName
        * @param: choice
        * @return returnName
        **
    **/
    public String produceName(int choice){
        String returnName;
        switch (choice){
            case 0:
                returnName = faker.cat().name();
                break;
            case 1:
                returnName = faker.dog().name();
                break;
            case 2:
                returnName = faker.company().name();
                break;
            case 3:
                returnName=faker.color().name();
                break;
            case 4:
                returnName=faker.country().name();
                break;
            case 5:
                returnName=faker.currency().name();
                break;
            case 6:
                returnName=faker.gameOfThrones().city();
                break;
            case 7:
                returnName=faker.university().name();
                break;
            case 8:
                returnName=faker.country().capital();
                break;
            case 9:
                returnName=faker.artist().name();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
        return returnName;
    }



}

