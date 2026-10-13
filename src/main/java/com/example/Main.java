package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class Main{
    public static void main(String[] args) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root =  mapper.readTree(new File("D:/java/data.json"));

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the search field:");
        String searchField = sc.nextLine().trim();


        System.out.println("Enter the search value:");
        String searchValue = sc.nextLine().trim();

        System.out.println("Enter the  required fields separated by commas,or all:");
        String requestedFields = sc.nextLine().trim();

        String[] outputFields = requestedFields.split("\\s*,\\s*");

        for (JsonNode country : root.path("countries")){
            for (JsonNode state : country.path("states")){
                for (JsonNode school : state.path("schools")){
                    for (JsonNode department : school.path("departments")){
                        for (JsonNode student : department.path("students")){
                            Map<String, String> details = new LinkedHashMap<>();

                            details.put("countryId",country.path("countryId").asText());
                            details.put("countryName",country.path("countryName").asText());

                            details.put("stateId",state.path("stateId").asText());
                            details.put("stateName",state.path("stateName").asText());

                            details.put("schoolId",school.path("schoolId").asText());
                            details.put("schoolName",school.path("schoolName").asText());

                            details.put("departmentId",department.path("departmentId").asText());
                            details.put("departmentName",department.path("departmentName").asText());

                            student.fields().forEachRemaining(entry-> details.put(entry.getKey(),entry.getValue().asText()));

                            String actualValue = null;

                            for(Map.Entry<String,String> entry: details.entrySet()) {
                                if (entry.getKey().equalsIgnoreCase(searchField)) {
                                    actualValue = entry.getValue();
                                    break;
                                }
                            }
                            if(actualValue != null && actualValue.equalsIgnoreCase(searchValue.trim())){


                                        if(requestedFields.equalsIgnoreCase("all")){
                                            details.forEach((key,value)->System.out.println(key+ " : " +value));
                                            System.out.println();

                                        }else{
                                            for (String field: outputFields){
                                                String output = null;
                                                for(Map.Entry<String,String> entry : details.entrySet()){
                                                    if(entry.getKey().equalsIgnoreCase(field.trim())){
                                                        output = entry.getValue();
                                                        break;
                                                    }
                                                }
                                                if(output !=null){
                                                    System.out.println(field.trim() + " : " + output);
                                                }else{
                                                    System.out.println("unknown field:" + field.trim());
                                                }
                                            }
                                            System.out.println();
                                    }
                            }
                        }
                    }
                }
            }
        }
        }

}
