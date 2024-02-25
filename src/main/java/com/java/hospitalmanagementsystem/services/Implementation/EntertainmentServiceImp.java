package com.java.hospitalmanagementsystem.services.Implementation;

import com.java.hospitalmanagementsystem.models.Entertainment;
import com.java.hospitalmanagementsystem.models.EntertainmentType;
import com.java.hospitalmanagementsystem.repositories.EntertainmentRepository;
import com.java.hospitalmanagementsystem.repositories.EntertainmentTypeRepository;
import com.java.hospitalmanagementsystem.services.EntertainmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.java.hospitalmanagementsystem.util.Tools.parseDate;

/**
 * Implementation for managing entertainment-related services in the application.
 *
 * <p>This service provides methods for retrieving entertainment types and elements, as well as
 * updating prices for different types of entertainment services. It allows querying entertainment
 * options based on availability and managing their pricing.
 */
@Service
@RequiredArgsConstructor
public class EntertainmentServiceImp implements EntertainmentService {

  private final EntertainmentTypeRepository entertainmentTypeRepository;
  private final EntertainmentRepository entertainmentRepository;

  @Override
  public List<EntertainmentType> getAllEntertainmentTypes() {
    return entertainmentTypeRepository.findAll();
  }

    @Override
    public Entertainment add(Entertainment entertainment ) {
        entertainmentRepository.save(entertainment);
        return entertainment;
    }

    @Override
    public Entertainment getById(int id){
      Optional<Entertainment> entertainment = entertainmentRepository.findById(id);
      if(entertainment.isEmpty()){
          throw new NoSuchElementException("Entertainment not found");
      }
      return entertainment.get();
    }

    @Override
    public List<Entertainment> getAll(){
      return entertainmentRepository.findAll();
    }

  @Override
  public List<Entertainment> getAllEntertainmentElementsByAvailableDate(
      String entertainmentType, String dateFrom, String timeFrom, String dateTo, String timeTo) {
    Timestamp dateTimeFrom = parseDate(dateFrom, timeFrom);
    Timestamp dateTimeTo = parseDate(dateTo, timeTo);

    List<EntertainmentType> entertainmentTypes = entertainmentTypeRepository.findAll();
    Optional<EntertainmentType> type =
        entertainmentTypes.stream()
            .filter(entertainmentType1 -> entertainmentType1.getName().equals(entertainmentType))
            .findFirst();
    if (type.isEmpty()) {
      throw new IllegalArgumentException("Entertainment type not found");
    }

    return entertainmentRepository.findAvailableEntertainmentsByTypeAndTime(
        type.get().getId(), dateTimeFrom, dateTimeTo);
  }

  @Override
  public void updatePrices(List<EntertainmentType> entertainmentTypes) {
    entertainmentTypes.forEach(
        entertainmentType -> {
          Optional<EntertainmentType> type =
              entertainmentTypeRepository.findById(entertainmentType.getId());
          if (type.isEmpty()) {
            throw new IllegalArgumentException("Entertainment type not found");
          }
          System.out.println(entertainmentType.getPrice());
          type.get().setPrice(entertainmentType.getPrice());
          entertainmentTypeRepository.save(type.get());
        });
  }
}
