package com.java.hotelmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * Represents a dish in a menu, including its details like name, description, and photo. This entity
 * is mapped to the "Dish" table in the database. Each dish can be associated with multiple menu
 * items.
 */
@Data
@Entity
@Table(name = "Dish")
public class Dish {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @NotBlank(message = "Name is required")
  @Size(min = 3, max = 50, message = "Name cannot be less that 3 and more than 50 characters")
  @Column(name = "name", nullable = false)
  private String name;

  @NotBlank(message = "Description is required")
  @Size(
      min = 3,
      max = 500,
      message = "Description cannot be less that 3 and more than 500 characters")
  @Column(name = "description", nullable = false)
  private String description;

  @NotBlank(message = "Photo is required")
  @Column(name = "photo_directory")
  private String photoDirectory;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "dish")
  private List<MenuItem> menuItems;

  public Dish() {}

  /**
   * Constructs a Dish instance with the specified name, description, and photo directory.
   *
   * @param name The name of the dish.
   * @param description The description of the dish.
   * @param photoDirectory The directory path to the dish's photo.
   */
  public Dish(String name, String description, String photoDirectory) {
    this.name = name;
    this.description = description;
    this.photoDirectory = photoDirectory;
  }
}
