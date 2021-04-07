package com.example.operationsunlight;

public class Plant {
    String common_name;
    String scientific_name;
    String family_common_name;
    String image_url;

    public Plant(String cn, String sn, String fcn, String iu){
        if(iu.isEmpty()){
            this.common_name = cn;
            this.scientific_name = sn;
            this.family_common_name = fcn;
            this.image_url = "https://cdn3.iconfinder.com/data/icons/file-and-folder-fill-icons-set/144/File_Error-512.png";
        }else{
            this.common_name = cn;
            this.scientific_name = sn;
            this.family_common_name = fcn;
            this.image_url = iu;
        }
    }

    public String getCommon_name() {
        return common_name;
    }

    public String getScientific_name() {
        return scientific_name;
    }

    public String getImage_url() {
        return image_url;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "common_name='" + common_name + '\'' +
                ", scientific_name='" + scientific_name + '\'' +
                ", family_common_name='" + family_common_name + '\'' +
                ", image_url='" + image_url + '\'' +
                '}' + "\n";
    }
}
