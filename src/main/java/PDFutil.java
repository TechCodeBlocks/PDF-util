import java.io.File;
import java.nio.file.Path;

public class PDFutil {

    public static void main(String[] args) {

    }


    private void crawlFiles(File directory){
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
                            //Do conversion logic for docx files
                            break;
                        case "doc":
                            //cl
                            break;
                        case "pptx":
                            //cl
                            break;
                        case "ppt":
                            //cl
                            break;
                        case "xlsx":
                            //cl
                            break;
                        case "xls":
                            //cl
                            break;
                        case "pages":
                            //cl
                            break;
                        case "key":
                            //cl
                            break;
                        case "numbers":
                            //cl
                            break;
                        case "jpg":
                            //al
                            break;
                        case "png":
                            //al
                            break;
                        case "jpeg":
                            //al
                            break;
                        case "pdf":
                            //ml
                            break;
                        default:
                            break;
                    }
                }
            }
        }

    }
}
