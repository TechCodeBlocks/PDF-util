package com.benjaminsolomons;

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
import java.util.List;
import java.util.Scanner;

public class PDFutil {
    static String basePath;
    static List<File> pdfsToMerge = new ArrayList<File>();
    static String finalDestinationFileName;
    static String apiKey;

    public static void main(String[] args) {
       Console console = System.console();
       basePath = console.readLine("Enter the root folder you wish to use: ");
       apiKey = console.readLine("Enter a valid ConvertAPI key: ");
       finalDestinationFileName = console.readLine("Please enter the desired name for your final document: ");


        //create a new PDF document at the base path

    }


    private static void crawlFiles(File directory){
        File[] files = directory.listFiles();
        if(files != null){
            for(File file: files){
                if(file.isDirectory()){
                    crawlFiles(file);
                }else{
                    String[] fileDetails = file.getName().split(".");
                    //Carry out conversion logic for specific file types. All other types of file will be ignored as they are uncommon or cannot be converted
                    //cl: conversion logic. Convert using the API then save as a temporary file, merge and delete.
                    //al: add logic. Create a new PDF, add image to it, merge with main PDF.
                    //ml: merge logic. File is already a pdf. Merge it with the main one.
                    switch (fileDetails[fileDetails.length-1]){
                        case "docx":
                            convertToPDF("docx",file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                            break;
                        case "doc":
                            convertToPDF("doc",file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                            break;
                        case "pptx":
                            convertToPDF("pptx",file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                            break;
                        case "ppt":
                            convertToPDF("ppt",file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                            break;
                        case "xlsx":
                            convertToPDF("xlsx",file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                            break;
                        case "xls":
                            convertToPDF("xls",file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                            break;
                        case "pages":
                            convertToPDF("pages",file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                            break;
                        case "key":
                            convertToPDF("key",file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                            break;
                        case "numbers":
                            convertToPDF("numbers",file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
                            break;
                        case "jpg":
                        case "jpeg":
                        case "png":
                            imgToPdf(file.getPath());
                            pdfsToMerge.add(new File(file.getPath() + ".pdf"));
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
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static void mergePDFs(){
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        mergerUtility.setDestinationFileName(Paths.get(basePath + finalDestinationFileName + ".pdf").toString());
        try {
            for (File file : pdfsToMerge) {
                mergerUtility.addSource(file);
            }
            mergerUtility.mergeDocuments();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
