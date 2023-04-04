package com.driver.service;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AirportService {
    AirportRepository repo = new AirportRepository();
//    addAirport
    public void addAirport(Airport port){
        repo.addAirport(port);
    }
    public String getLargestAirportName(){
       List<String> portList= repo.getLargestAirportName();
       System.out.print(portList);
       if(portList.size() > 0) {
           String smallPort = portList.get(0);
           for(int i=1;i<portList.size();i++){
               System.out.println(portList.get(i).compareTo(smallPort));
              if(portList.get(i).compareTo(smallPort) < 0){
                  smallPort=portList.get(i);
              }
           }
           return smallPort;
       }
       return null;
    }
    public void addFlight(Flight flight){
        repo.addFlight(flight);
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City from , City two){
       List<Double> timeList= repo.getShortestDurationOfPossibleBetweenTwoCities(from,two);
       if(timeList.size()>0) {
           double min = timeList.get(0);
           for (int i = 1; i < timeList.size(); i++) {
            if(timeList.get(i) < min){
                min=timeList.get(i);
            }
           }
           return min;
       }
       return -1;
    }
    public String getAirportNameFromFlightId(int id){
        return repo.getAirportNameFromFlightId(id);
    }
    public String addPassenger(Passenger passenger){
       return repo.addPassenger(passenger);
    }
    public String bookATicket(int flightId , int passengerId){
        return repo.bookATicket(flightId,passengerId);
    }
//    cancelATicket
    public String cancelATicket(int flightId , int passengerId){
    return repo.cancelATicket(flightId,passengerId);

    }
//    countOfBookingsDoneByPassengerAllCombined
    public int countOfBookingsDoneByPassengerAllCombined( int passengerId){
        return repo.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public int calculateFlightFare(int flightId){
        int size= repo.calculateFlightFare(flightId);

        if(size > 0){
            return 3000+size*50;
        }

        return 3000;

    }
    public int calculateRevenueOfAFlight (int flightId){
        return repo.calculateRevenueOfAFlight(flightId);
    }
    public int getNumberOfPeopleOn(Date date , String airportName){
        return repo.getNumberOfPeopleOn(date,airportName);
    }
}
