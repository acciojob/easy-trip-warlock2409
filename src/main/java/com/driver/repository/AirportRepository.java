package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class AirportRepository {
    HashMap<String, Airport> airPortRepo = new HashMap<>();
    HashMap<Integer, Flight> flightRepo = new HashMap<>();

    HashMap<Integer, Passenger> passengerRepo = new HashMap<>();

    HashMap<Integer, HashSet<Integer>> bookingRepo = new HashMap<>();


//    addAirport
    public void addAirport(Airport port){
        airPortRepo.put(port.getAirportName(),port);
        System.out.println(airPortRepo);
    }
    public List<String> getLargestAirportName(){
        int large=-1;

        for(String a : airPortRepo.keySet()){
            Airport p = airPortRepo.get(a);
            if(large < p.getNoOfTerminals()){
                large=p.getNoOfTerminals();
            }
        }

        List<String> portList = new ArrayList<>();

        for(String a : airPortRepo.keySet()){
            if(airPortRepo.get(a).getNoOfTerminals()== large){
                portList.add(airPortRepo.get(a).getAirportName());
            }
        }
        System.out.println(portList);
        return portList;
    }
//    addFlight
    public void addFlight(Flight flight){
        flightRepo.put(flight.getFlightId(),flight);
        System.out.print(flightRepo);
    }
//    getShortestDurationOfPossibleBetweenTwoCities
    public List<Double> getShortestDurationOfPossibleBetweenTwoCities( City from , City to){
        List<Double> timeList = new ArrayList<>();
        for(Integer i : flightRepo.keySet()){
            if(flightRepo.get(i).getFromCity().equals(from) && flightRepo.get(i).getToCity().equals(to)){
                timeList.add(flightRepo.get(i).getDuration());
            }
        }
        return timeList;
    }
    public String getAirportNameFromFlightId(int id){
        if(flightRepo.containsKey(id)) {
            Flight flight = flightRepo.get(id);
            for(String port : airPortRepo.keySet()){
                if(airPortRepo.get(port).getCity().equals(flight.getFromCity())){
                    return flight.getFromCity().toString();
                }
            }
        }
        return null;
    }
    public String addPassenger(Passenger passenger){
        passengerRepo.put(passenger.getPassengerId(),passenger);
        System.out.println(passengerRepo);
        return "SUCCESS";
    }
    public String bookATicket(int flightId , int passengerId) {

        if(bookingRepo.containsKey(flightId)){
            if(bookingRepo.get(flightId).contains(passengerId)){
               return "FAILURE";
            }
        }

        if(!passengerRepo.containsKey(passengerId)){
            return "FAILURE";
        }

        if(flightRepo.containsKey(flightId) && flightRepo.get(flightId).getMaxCapacity()>0){

            int capacity =flightRepo.get(flightId).getMaxCapacity();
            capacity--;
            flightRepo.get(flightId).setMaxCapacity(capacity);
            HashSet<Integer> hs;
            if(!bookingRepo.containsKey(flightId))
                hs = new HashSet<>();
            else{
                hs = bookingRepo.get(flightId);
            }
            hs.add(passengerId);

            bookingRepo.put(flightId,hs);

            return "SUCCESS";
        }
        return "FAILURE";
    }
//    cancelATicket
    public String cancelATicket(int flightId , int passengerId) {

        if(bookingRepo.containsKey(flightId) && bookingRepo.get(flightId).contains(passengerId)){

            if(flightRepo.containsKey(flightId) ){
                int capacity =flightRepo.get(flightId).getMaxCapacity();
                capacity++;
                flightRepo.get(flightId).setMaxCapacity(capacity);
                HashSet<Integer> hs = bookingRepo.get(flightId);
                hs.remove(passengerId);
                return "SUCCESS";
            }

        }


        return "FAILURE";
    }
     public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){

        int count =0;
        for(Integer flight : bookingRepo.keySet()){
            if(bookingRepo.get(flight).contains(passengerId)){
                count++;
            }
        }
        return count;
     }
     public int calculateFlightFare(Integer flightId){
        if(bookingRepo.containsKey(flightId)){
            return bookingRepo.get(flightId).size();
        }
        return 0;
     }
//     calculateRevenueOfAFlight
    public int calculateRevenueOfAFlight(int flightId){
        int noOfPassenger=0;
        int revenue=0;
        if(bookingRepo.containsKey(flightId)){
            noOfPassenger= bookingRepo.get(flightId).size();
            if(noOfPassenger>0){
                revenue=3000;
            }
            for(int i=1;i<noOfPassenger;i++){
                revenue+=3000+50*i;
            }
        }
        return revenue;
    }
    public int getNumberOfPeopleOn(Date date , String airportName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int count =0;
        if(airPortRepo.containsKey(airportName)) {
            City fromCity = airPortRepo.get(airportName).getCity();

            for (int flId : bookingRepo.keySet()) {
                if (flightRepo.containsKey(flId) && flightRepo.get(flId).getFromCity().equals(fromCity)) {

                    String formattedDate = dateFormat.format(flightRepo.get(flId).getFlightDate());
                    String requiredDate = dateFormat.format(date);
                    if(requiredDate.equals(formattedDate)){
                        count+=bookingRepo.get(flId).size();
                    }
//                    System.out.print(flightRepo.get(flId).getFlightDate()+" "+formattedDate+" "+requiredDate);
                }
                if (flightRepo.containsKey(flId) && flightRepo.get(flId).getToCity().equals(fromCity)) {

                    String formattedDate = dateFormat.format(flightRepo.get(flId).getFlightDate());
                    String requiredDate = dateFormat.format(date);
                    if(requiredDate.equals(formattedDate)){
                        count+=bookingRepo.get(flId).size();
                    }
//                    System.out.print(flightRepo.get(flId).getFlightDate()+" "+formattedDate+" "+requiredDate);
                }

            }
        }
        return count;
    }
}
