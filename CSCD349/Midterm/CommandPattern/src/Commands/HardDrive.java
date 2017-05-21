package Commands;

public class HardDrive {
    public boolean format, copy, unpack, install, defragment;
    void setFormat(){
        this.format = true;
    }
    void setCopy(){
        this.copy = true;
    }
    void setUnpack(){
        this.unpack = true;
    }
    void setInstall(){
        this.install = true;
    }
    void setDefragment(){
        this.defragment = true;
    }
}
