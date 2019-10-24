import java.util.*; 
import java.text.*;
import java.math.*;
public class Concert{
    /** ticket prices for the different sections */
    private static final double priceUpper = 29.90;
    private static final double priceLower = 99.0;
    private static final double priceFloor = 180.0;
    /** total number of tickets per section */
    private static final int totalUpper= 300;
    private static final int totalLower= 300;
    private static final int totalFloor= 400;
    /** number of tickets sold per section */
    private int soldUpper = 0;
    private int soldLower = 0;
    private int soldFloor = 0;
    /** sales */
    private double salesUpper = 0;
    private double salesLower = 0;
    private double salesFloor = 0;
    private double totalSales = 0;
    /** remaining number of tickets per section */
    private int avalUpper = totalUpper;
    private int avalLower = totalLower;
    private int avalFloor = totalFloor;
    /** concert information */
    private String artistName = "";
    private String venueName = "";
    private int mnth, day, yr;
    private boolean isLeapYear(int y){
        boolean rtn = false;
        if(y % 4 == 0){
            rtn = true;
        }
        if(y % 100 == 0){
            rtn = false;
        }
        if(y % 400 == 0){
            rtn = true;
        }
        return rtn;
    }

    private boolean isDateValid(int m, int d, int y){
        boolean rtn = false;
        if((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 ||
        m == 12) && (d >= 1 && d <= 31 && y >= 0)){
            rtn = true;
        }
        if((m == 4 || m == 6 || m == 9 || m == 11) && (d >= 1 && d <= 30 &&
        y >= 0)){
            rtn = true;
        }
        if(m == 2 && y >= 0){
            if(isLeapYear(y) == true && d >= 1 && d <= 29){
                rtn = true;
            }
            if(isLeapYear(y) == false && d >=1 && d <= 28){
                rtn = true;
            }
        }
        return rtn;
    }
    
    private double round(double pmt){
        BigDecimal p = new BigDecimal(Double.toString(pmt));
        p = p.setScale(2, RoundingMode.HALF_UP);
        return p.doubleValue();
    }

    private void parseDate(String date){
        int firstSlash = date.indexOf("/");
        int secondSlash = date.indexOf("/", firstSlash + 1);
        if(isDateValid(Integer.parseInt(date.substring(0, firstSlash)), 
            Integer.parseInt(date.substring(firstSlash + 1, secondSlash)),
            Integer.parseInt(date.substring(secondSlash + 1))) == false){
            System.out.println("Invalid date entered.");
        }
        else{
            mnth = Integer.parseInt(date.substring(0, firstSlash));
            day = Integer.parseInt(date.substring(firstSlash + 1, secondSlash));
            yr = Integer.parseInt(date.substring(secondSlash + 1));
        }
    }

    public Concert (){
        mnth = 9;
        day = 8;
        yr = 2019;
        artistName = "Jonas Brothers";
        venueName = "Van Andel Arena";
    }

    public Concert (int m, int d, int y, String a, String v){
        if(isDateValid(m, d, y) == true){
            mnth = m;
            day = d;
            yr = y;
        }
        else{
            mnth = 0;
            day = 0;
            yr = 0;
        }
        artistName = a;
        venueName = v;
    }

    public Concert (String date, String a, String v){
        artistName = a;
        venueName = v;
        parseDate(date);
    }

    public String getArtist(){
        return artistName;
    }

    public String getVenue(){
        return venueName;
    }

    public double getTicketPrice(char x){
        if(x == 'U' || x == 'u'){
            return priceUpper;
        }
        if(x == 'L' || x == 'l'){
            return priceLower;
        }
        if(x == 'F' || x == 'f'){
            return priceFloor;
        }
        else{
            return 0;
        }
    }

    public int getAvailableLowerTickets(){
        return avalLower;
    }

    public int getAvailableUpperTickets(){
        return avalUpper;
    }

    public int getAvailableFloorTickets(){
        return avalFloor;
    }

    public double getTotalSales(){
        return totalSales;
    }

    public int getMonth(){  
        return mnth;
    }

    public int getDay(){
        return day;
    }

    public int getYear(){
        return yr;
    }

    public void setArtist(String a){
        artistName = a;
    }

    public void setVenue(String v){
        venueName = v;
    }

    public void setDate(int m, int d, int y){
        if(isDateValid(m, d, y) == true){
            mnth = m;
            day = d;
            yr = y;
        }
        else{
            mnth = mnth;
            day = day;
            yr = yr;
        }
    }

    public void buyTickets(char type, int num, double pmt){
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        pmt = round(pmt);
        double saleCost = 0;
        double n = num;
        if(type == 'u' || type == 'U'){
            saleCost = round(priceUpper * n);
            if(pmt >= saleCost && soldUpper + num <= avalUpper && num > 0){
                soldUpper = soldUpper + num;
                avalUpper = avalUpper - num;
                salesUpper = salesUpper + saleCost;
                totalSales = totalSales + saleCost;
                System.out.println("Sale of " + num + 
                    " tickets in upper section.");
                System.out.println("Total due: " + currency.format(saleCost));
                System.out.println("Change: " + 
                    currency.format((pmt - saleCost)));
                System.out.println("Thank you for your business!");
            }
            else{
                System.out.println("Sale could not be completed, please");
                System.out.println("ensure tickets are available in your");
                System.out.println("requested section and payment matches");
                System.out.println("ticket cost. Please note that quantity");
                System.out.println("of tickets must exceed 0, and sections");
                System.out.println("must be represented by either U, L or F");
            }
        }
        if(type == 'l' || type == 'L'){
            saleCost = round(priceLower * n);
            if(pmt >= saleCost && soldLower + num <= avalLower && num > 0){
                soldLower = soldLower + num;
                avalLower = avalLower - num;
                salesLower = salesLower + saleCost;
                totalSales = totalSales + saleCost;
                System.out.println("Sale of " + num +
                    " tickets in lower section.");
                System.out.println("Total due: " + currency.format(saleCost));
                System.out.println("Change: " + 
                    currency.format((pmt - saleCost)));
                System.out.println("Thank you for your business!");
            }
            else{
                System.out.println("Sale could not be completed, please");
                System.out.println("ensure tickets are available in your");
                System.out.println("requested section and payment matches");
                System.out.println("ticket cost. Please note that quantity");
                System.out.println("of tickets must exceed 0, and sections");
                System.out.println("must be represented by either U, L or F");
            }
        }
        if(type == 'f' || type == 'F'){
            saleCost = round(priceFloor * n);
            if(pmt >= saleCost && soldFloor + num <= avalFloor && num > 0){
                soldFloor = soldFloor + num;
                avalFloor = avalFloor - num;
                salesFloor = salesFloor + saleCost;
                totalSales = totalSales + saleCost;
                System.out.println("Sale of " + num +
                    " tickets in floor section.");
                System.out.println("Total due: " + currency.format(saleCost));
                System.out.println("Change: " + 
                    currency.format((pmt - saleCost)));
                System.out.println("Thank you for your business!");
            }
            else{
                System.out.println("Sale could not be completed, please");
                System.out.println("ensure tickets are available in your");
                System.out.println("requested section and payment matches");
                System.out.println("ticket cost. Please note that quantity");
                System.out.println("of tickets must exceed 0, and sections");
                System.out.println("must be represented by either U, L or F");
            }
        }
    }

    public void printReport(){
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println("Concert Sales Report");
        System.out.println("----------------------------------------------");
        System.out.println("Artist:         " + artistName);
        System.out.println("Venue:          " + venueName);
        System.out.println("Date:           " + mnth + "/" + day + "/" + yr);
        System.out.println("");
        System.out.println("Tickets Sold");
        System.out.println("----------------------------------------------");
        System.out.println("Upper:  " + soldUpper + "           " +
            currency.format(salesUpper));
        System.out.println("Lower:  " + soldLower + "           " +
            currency.format(salesLower));
        System.out.println("Floor:  " + soldFloor + "           " +
            currency.format(salesFloor));
        System.out.println("----------------------------------------------");
        System.out.println("Total Sales:        " + currency.format(totalSales));        
    }

    public void simulateCompanyBuyingTickets(int num){
        char type = 'u';
        int n = 0, u = 0, l = 0, f = 0, r = 0;
        double pmtu = 0, pmtl = 0, pmtf = 0;
        Random rng = new Random();
        while (r != num){
            n = rng.nextInt(3);
            if(n == 0){
                type = 'u';
                pmtu = pmtu + priceUpper;
                u++;
            }
            if(n == 1){
                type = 'l';
                pmtl = pmtl + priceLower;
                l++;
            }
            if(n == 2){
                type = 'f';
                pmtf = pmtf + priceFloor;
                f++;
            }
            r++;
        }
        buyTickets('u', u, pmtu);
        buyTickets('l', l, pmtl);
        buyTickets('f', f, pmtf);
    }

    public String formatDate(int f){
        String date = "";  
        if(f == 1){
            date = mnth + "/" + day + "/" + yr;
        }
        if(f == 2){
            if(mnth >= 10){
                if(day >= 10){
                    date = mnth + "/" + day + "/" + yr;
                }
                if(day <= 9){
                    date = mnth + "/0" + day + "/" + yr;
                }
            }
            if(mnth <= 9){
                if(day >= 10){
                    date = "0" + mnth + "/" + day + "/" + yr;
                }
                if(day <= 9){
                    date = "0" + mnth + "/0" + day + "/" + yr;
                }
            }
        }
        if(f == 3){
            String months = "JanFebMarAprMayJunJulAugSepOctNovDec".substring
            ((mnth * 3) - 3, (mnth * 3));
            date = months + " " + day + ", " + yr;
        }
        if(f == 4){
            String months = "";
            if(mnth == 1){
                months = "January";
            }
            if(mnth == 2){
                months = "February";
            }
            if(mnth == 3){
                months = "March";
            }
            if(mnth == 4){
                months = "April";
            }
            if(mnth == 5){
                months = "May";
            }
            if(mnth == 6){
                months = "June";
            }
            if(mnth == 7){
                months = "July";
            }
            if(mnth == 8){
                months = "August";
            }
            if(mnth == 9){
                months = "September";
            }
            if(mnth == 10){
                months = "October";
            }
            if(mnth == 11){
                months = "November";
            }
            if(mnth == 12){
                months = "December";
            }
            date = months + " " + day + ", " + yr;
        }
        return date;
    }
}