package ServicePackage;

public abstract class Service {

        String description = "Unknown Service";
        public String getDescription(){
            return description;
        }
        public abstract double cost();
}
