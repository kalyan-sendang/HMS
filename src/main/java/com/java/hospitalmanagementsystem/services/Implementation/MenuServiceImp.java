package com.java.hospitalmanagementsystem.services.Implementation;

import com.java.hospitalmanagementsystem.models.Dish;
import com.java.hospitalmanagementsystem.models.MenuItem;
import com.java.hospitalmanagementsystem.models.MenuType;
import com.java.hospitalmanagementsystem.models.dto.restaurant.MenuForDayResponse;
import com.java.hospitalmanagementsystem.repositories.DishRepository;
import com.java.hospitalmanagementsystem.repositories.MenuItemRepository;
import com.java.hospitalmanagementsystem.repositories.MenuTypeRepository;
import com.java.hospitalmanagementsystem.services.MenuService;
import com.java.hospitalmanagementsystem.util.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementation for managing the menu services in the application.
 *
 * <p>This service provides methods for retrieving and managing different meal menus (breakfast,
 * lunch, dinner) and specific menus for particular days. It includes functionalities for adding,
 * updating, and deleting dishes from the menu.
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImp implements MenuService {

  private final DishRepository dishRepository;
  private final MenuTypeRepository menuTypeRepository;
  private final MenuItemRepository menuItemRepository;

  @Override
  public List<Dish> getBreakfastMenu() {
    return dishRepository.findDishesByMenuTypeNameAndMenuDate(
        MenuType.MenuEnum.BREAKFAST, Date.valueOf(LocalDate.now()));
  }

  @Override
  public List<Dish> getLunchMenu() {
    return dishRepository.findDishesByMenuTypeNameAndMenuDate(
        MenuType.MenuEnum.LUNCH, Date.valueOf(LocalDate.now()));
  }

  @Override
  public List<Dish> getDinnerMenu() {
    return dishRepository.findDishesByMenuTypeNameAndMenuDate(
        MenuType.MenuEnum.DINNER, Date.valueOf(LocalDate.now()));
  }

  @Override
  public List<MenuForDayResponse> getMenuForDay(String date) {
    Date dateSql = Tools.parseDate(date);
    return dishRepository.findDishesByMenuDate(dateSql);
  }

  @Override
  public void deleteFromDayMenu(String date, Integer id, MenuType.MenuEnum menuTypeName) {
    Date dateSql = Tools.parseDate(date);
    dishRepository.deleteFromDayMenu(dateSql, id, menuTypeName);
  }

  @Override
  public List<Dish> getDishes() {
    return dishRepository.findAll();
  }

  @Override
  public void addDishToTheDayMenu(String date, Integer id, MenuType.MenuEnum menuTypeName) {
    Date dateSql = Tools.parseDate(date);
    Dish dish = dishRepository.findById(id).orElseThrow();
    MenuType menuType = menuTypeRepository.findByName(menuTypeName);
    MenuItem menuItem = new MenuItem(dateSql, dish, menuType);
    menuItemRepository.save(menuItem);
  }

  @Override
  public void addDish(Dish dish) {
    dishRepository.save(dish);
  }

  @Override
  public void deleteDish(Integer id) {
    dishRepository.deleteById(id);
  }

  @Override
  public void updateDish(Integer id, Dish dish) {
    Dish dishFromDb = dishRepository.findById(id).orElseThrow();
    dishFromDb.setName(dish.getName());
    dishFromDb.setDescription(dish.getDescription());
    dishFromDb.setPhotoDirectory(dish.getPhotoDirectory());
    dishRepository.save(dishFromDb);
  }
}
