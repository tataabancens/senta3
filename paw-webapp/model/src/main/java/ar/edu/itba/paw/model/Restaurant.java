package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.ReservationStatus;

import javax.persistence.*;
import java.util.*;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_restaurantid_seq")
    @SequenceGenerator(sequenceName = "restaurant_restaurantid_seq", name = "restaurant_restaurantid_seq", allocationSize = 1)
    @Column(name = "restaurantid")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String restaurantName;

    @Column(length = 100, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String mail;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> dishes = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishCategory> dishCategories = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "userid")
    private User user;

    @Column(nullable = false)
    private Integer totalChairs;

    @Column(nullable = false)
    private Integer openHour;

    @Column(nullable = false)
    private Integer closeHour;

    @Column(nullable = false)
    private Integer pointsForDiscount;

    @Column(nullable = false)
    private Float discountCoefficient;

    @Column(nullable = false)
    private Float addPointsCoefficient;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /* default */ Restaurant() {
        // Just for hibernate
    }

    public Restaurant(String restaurantName, String phone, String mail, int totalChairs, int openHour, int closeHour, int pointsForDiscount) {
        super();
        this.restaurantName = restaurantName;
        this.phone = phone;
        this.mail = mail;
        this.totalChairs = totalChairs;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.pointsForDiscount = pointsForDiscount;
    }

    @Deprecated
    public Restaurant(long id, String restaurantName, String phone, String mail, int totalChairs, int openHour, int closeHour) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.phone = phone;
        this.mail = mail;
        this.totalChairs = totalChairs;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    public Dish createDish(String dishName, String dishDescription, double price, long imageId, DishCategory category) {
        Dish dish = new Dish(this, dishName, (int) price,  dishDescription, imageId, category);
        dishes.add(dish);
        return dish;
    }

    public DishCategory createDishCategory(String categoryName) {
        DishCategory category = new DishCategory(this, categoryName.toUpperCase());
        dishCategories.add(category);
        return category;
    }

    public List<Reservation> getReservationsByStatusList(List<ReservationStatus> statusList) {
        List<Reservation> toRet = new ArrayList<>(reservations);
        toRet.removeIf(r -> !statusList.contains(r.getReservationStatus()));
        return toRet;
    }

    public List<DishCategory> getDishCategories() {
        return new ArrayList<>(dishCategories);
    }

    public void setDishCategories(List<DishCategory> dishCategories) {
        this.dishCategories = dishCategories;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public long getId() {
        return id;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public List<Dish> getDishesByCategory(DishCategory category) {
        return category.getDishes();
    }

    public Map<DishCategory, String> getDishCategoriesAsMap() {
        Map<DishCategory, String> toRet = new LinkedHashMap<>();
        for (DishCategory category: getDishCategories()) {
            toRet.put(category, category.getName());
        }
        return toRet;
    }

    public int getTotalChairs() {
        return totalChairs;
    }

    public int getOpenHour() {
        return openHour;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTotalChairs(Integer totalChairs) {
        this.totalChairs = totalChairs;
    }

    public void setOpenHour(Integer openHour) {
        this.openHour = openHour;
    }

    public void setCloseHour(Integer closeHour) {
        this.closeHour = closeHour;
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getDishCategoryOfId(long categoryId) {
        for(DishCategory category : this.dishCategories){
            if(category.getId()==categoryId){
                return category.getName();
            }
        }
        return this.dishCategories.get(0).getName();
    }

    public Dish getDishOfId(long id){
        for(Dish dish : this.dishes){
            if(dish.getId() == id){
                return dish;
            }
        }
        return null;
    }

    public Object canCategoryBeDeleted(long categoryId) {
//        if(categoryId == 1){
//            return false;
//        }
        for(DishCategory category : this.dishCategories){
            if(category.getId()==categoryId){
                return category.getDishes().isEmpty();
            }
        }
        return false;
    }

    public void deleteCategory(long categoryId) {
        this.dishCategories.removeIf(category -> category.getId() == categoryId);
    }

    public void deleteDish(long dishId) {
        this.dishes.removeIf(dish -> dish.getId() == dishId);
    }

    public Integer getPointsForDiscount() {
        return pointsForDiscount;
    }

    public void setPointsForDiscount(Integer pointsForDiscount) {
        this.pointsForDiscount = pointsForDiscount;
    }

    public Float getDiscountCoefficient() {
        return discountCoefficient;
    }

    public void setDiscountCoefficient(Float discountCoefficient) {
        this.discountCoefficient = discountCoefficient;
    }

    public Float getAddPointsCoefficient() {
        return addPointsCoefficient;
    }

    public void setAddPointsCoefficient(Float addPointsCoefficient) {
        this.addPointsCoefficient = addPointsCoefficient;
    }
}
