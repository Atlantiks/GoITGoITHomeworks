package homework13;

public class User {
     int id;
     String name;
     String email;
     Address address;
     String phone;
     String website;
     Company company;

    public User(Integer id, String name, String email, Address address, String phone, String website, Company company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
    }

    static class Address {
        String street;
        String suite;
        String city;
        String zipcode;
        Geo geo;

        public Address(String street, String suite, String city, String zipcode, Geo geo) {
            this.street = street;
            this.suite = suite;
            this.city = city;
            this.zipcode = zipcode;
            this.geo = geo;
        }
    }

    static class Geo {
        double lat;
        double lng;

        public Geo(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }
    }

    static class Company {
        String name;
        String catchPhrase;
        String bs;

        public Company(String name, String catchPhrase, String bs) {
            this.name = name;
            this.catchPhrase = catchPhrase;
            this.bs = bs;
        }
    }

    public Integer getId() {
        return id;
    }

}
