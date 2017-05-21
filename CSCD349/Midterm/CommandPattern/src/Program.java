import Commands.*;

public class Program {
    public static void main(String[] args) {
        HardDrive hdd = new HardDrive();
        Command[] commands = {new Format(hdd), new copy(hdd), new unpack(hdd), new install(hdd), new defragement(hdd)};
        Installer installer = new Installer(commands);
        installer.install();
        if(hdd.install && hdd.defragment)
            System.out.println("Installation Complete");
    }
}
