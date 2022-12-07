package com.mju.insuranceCompany;

import org.junit.jupiter.api.Test;

public class FileUploadTest {


    @Test
    void uploadFile() {
        String fName = "보헝.hwp";
        int extensionIndex = fName.indexOf(".");
        String extension = fName.substring(extensionIndex);
        System.out.println(extension);
    }
}
