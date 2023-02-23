package com.cmput301w23t47.canary.viewmodel;

import com.cmput301w23t47.canary.model.QRCode;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for QRCodeVMList
 * This is for the fragment screen the adapter and the will be somewhere else
 * requires QRCodeVMElement be used first on each element want to put in here
 */

public class QRCodeVMList {
    private List<QRCodeVMElement> QRListEle = new ArrayList<>();


    public QRCodeVMList() {
        // TODO Auto-generated constructor stub
    }
    public QRCodeVMList(QRCodeVMElement qrCodeVMElement) {
        // TODO Auto-generated constructor stub
        QRListEle.add(qrCodeVMElement);
    }

    public void addQRCodeVMList(QRCodeVMElement qrCodeVMElement) {
        QRListEle.add(qrCodeVMElement);
    }

    public void removeQRCodeVMList(QRCodeVMElement qrCodeVMElement) {
        QRListEle.remove(qrCodeVMElement);
    }


}
