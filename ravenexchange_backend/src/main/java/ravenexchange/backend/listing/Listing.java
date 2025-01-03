package ravenexchange.backend.listing;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "listing")
@Data
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "listing_id")
    private Long listingId;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "listing_name")
    private String listingName;

    @Column(name = "listing_description")
    private String listingDescription;

    @Column(name = "listing_price")
    private double listingPrice;

    @Column(name = "listing_picture")
    private String[] listingPictures;

    @Column(name = "sold_status")
    private boolean soldStatus = false;

    @Column(name = "listing_timestamp")
    private Timestamp listingTimestamp;

    @Column(name = "listing_category")
    private String listingCategory;

    public Listing() {}

    public Listing(Long sellerId, String listingName, String listingDescription, double listingPrice, String[] listingPictures, String listingCategory) {
        this.sellerId = sellerId;
        this.listingName = listingName;
        this.listingDescription = listingDescription;
        this.listingPrice = listingPrice;
        this.listingPictures = listingPictures;
        this.listingCategory = listingCategory;
    }

    @PrePersist
    public void onCreate() {
        listingTimestamp = Timestamp.valueOf(LocalDateTime.now());
    }

    public void setSoldStatus(boolean soldStatus) {
        this.soldStatus = soldStatus;
    }

    public void setListingPicture(String[] listingPicture) {
        this.listingPictures = listingPicture;
    }

    public void setListingCategory(String listingCategory) {
        this.listingCategory = listingCategory;
    }

    public void setListingPrice(double listingPrice) {
        this.listingPrice = listingPrice;
    }

    public void setListingDescription(String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getListingId() {
        return listingId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public String getListingName() {
        return listingName;
    }

    public String getListingDescription() {
        return listingDescription;
    }

    public double getListingPrice() {
        return listingPrice;
    }

    public String[] getListingPicture() {
        return listingPictures;
    }

    public boolean getSoldStatus() {
        return soldStatus;
    }

    public Timestamp getListingTimestamp() {
        return listingTimestamp;
    }

    public String getListingCategory() {
        return listingCategory;
    }

    //Converts the listing object to a map
    public Map<String, Object> toMap() {
        HashMap<String, Object> listingMap = new HashMap<>();
        listingMap.put("listing_id", listingId);
        listingMap.put("seller_id", sellerId);
        listingMap.put("listing_name", listingName);
        listingMap.put("listing_description", listingDescription);
        listingMap.put("listing_price", listingPrice);
        listingMap.put("listing_pictures", listingPictures);
        listingMap.put("sold_status", soldStatus);
        listingMap.put("listing_timestamp", listingTimestamp);
        listingMap.put("listing_category", listingCategory);

        return listingMap;
    }
}
