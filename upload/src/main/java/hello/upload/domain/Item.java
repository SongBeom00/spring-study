package hello.upload.domain;

import lombok.Data;

import java.util.List;

@Data
public class Item {

     private Long id;
     private String fileName;
     private UploadFile attachFile;
     private List<UploadFile> imageFiles;

}
