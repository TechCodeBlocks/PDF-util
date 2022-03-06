package com.benjaminsolomons;

import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.Console;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PDFutil {
    static String basePath;
    static List<File> pdfsToMerge = new ArrayList<File>();
    static String finalDestinationFileName;
    static String apiKey;

    public static void main(String[] args) {
        System.out.println("PDF-Util Version 1. © 2022 Benjamin Solomons");
       Console console = System.console();
       basePath = console.readLine("Enter the root folder you wish to use: ").replace("'","");
       apiKey = console.readLine("Enter a valid ConvertAPI key: ");
       finalDestinationFileName = console.readLine("Please enter the desired name for your final document: ");
//        basePath = "/home/benjaminsolomons/Documents/TestingStupidProgram";
//        apiKey = "A7yYTdLcZwOdMIRh";
//        finalDestinationFileName = "MergedPDF";
     Config.setDefaultSecret(apiKey);
       System.out.println("Processing and converting documents (this will take a while)....");
       crawlFiles(new File(basePath));
        System.out.println("Merging into one file (this may take a while)....");
       mergePDFs();
        System.out.println("Cleaning up temporary PDFS that the program created....");
        cleanup();
       System.out.println("Your merged PDF is created. It is called " + finalDestinationFileName + " in the folder above root folder");


        //create a new PDF document at the base path

    }


    private static void crawlFiles(File directory){
        File[] files = directory.listFiles();
        Arrays.sort(files);
        if(files != null){
            for(File file: files){
                if(file.isDirectory()){
                    System.out.println(file.getPath());
                    crawlFiles(file);
                }else {
                    String[] fileDetails = file.getName().split("\\.");
                    //Carry out conversion logic for specific file types. All other types of file will be ignored as they are uncommon or cannot be converted
                    //cl: conversion logic. Convert using the API then save as a temporary file, merge and delete.
                    //al: add logic. Create a new PDF, add image to it, merge with main PDF.
                    //ml: merge logic. File is already a pdf. Merge it with the main one.
//                   System.out.println(file.getName().split("\\.").length);
                    if (fileDetails.length > 0) {
                        System.out.println(file.getPath());
                        switch (fileDetails[fileDetails.length - 1]) {
                            case "docx":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                convertToPDF("docx", file.getPath());

                                break;
                            case "doc":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                convertToPDF("doc", file.getPath());

                                break;
                            case "pptx":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                convertToPDF("pptx", file.getPath());

                                break;
                            case "ppt":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                convertToPDF("ppt", file.getPath());

                                break;
                            case "xlsx":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                convertToPDF("xlsx", file.getPath());

                                break;
                            case "xls":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                convertToPDF("xls", file.getPath());

                                break;
                            case "pages":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                convertToPDF("pages", file.getPath());

                                break;
                            case "key":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                convertToPDF("key", file.getPath());

                                break;
                            case "numbers":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                convertToPDF("numbers", file.getPath());

                                break;
                            case "jpg":
                            case "jpeg":
                            case "png":
                                pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                                imgToPdf(file.getPath());

                                break;
                            case "pdf":
                                pdfsToMerge.add(file);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }

    }

    private static void convertToPDF(String fileType, String path){
        try {
            ConvertApi.convert(fileType, "pdf",
                    new Param("file", Paths.get(path))
            ).get().saveFile(Paths.get(path+".pdf"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static void imgToPdf(String path){
        try {
            PDDocument document = new PDDocument();
            PDImageXObject pdImageXObject = PDImageXObject.createFromFile(path, document);
            PDPage page = new PDPage(new PDRectangle(pdImageXObject.getWidth(), pdImageXObject.getHeight()));
            document.addPage(page);
            try (PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true))
            {
                float scale = 1f;
                cs.drawImage(pdImageXObject, 20,20, pdImageXObject.getWidth() * scale,pdImageXObject.getHeight()*scale );
            }
            document.save(path+".pdf");
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static void mergePDFs(){
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        System.out.println(Paths.get(basePath + finalDestinationFileName + ".pdf").toString());
        mergerUtility.setDestinationFileName(Paths.get(basePath + finalDestinationFileName + ".pdf").toString());
        try {
            for (File file : pdfsToMerge) {
                System.out.println("adding file to merger" + file.getPath());
                mergerUtility.addSource(file);
            }
            mergerUtility.mergeDocuments(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void cleanup(){
        for(File file: pdfsToMerge){
            if(file.getName().split("\\.").length > 2){
                file.delete();
            }
        }
    }

}
