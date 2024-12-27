package ravenexchange.backend.listing;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Map<String, Object>> getAllListings(int offset, int limit, String category) {
        List<Map<String, Object>> listings = new ArrayList<>();
        List<Listing> listingEntities;

        if(offset < 0 || limit <= 0){
            throw new IllegalArgumentException("Offset must be greater than or equal to 0 and limit must be greater than 0");
        }

        if(category == null){
            listingEntities = listingRepository.findTop10ByOrderByListingIdDesc(); //Find by id in descending order
        }
        else {
            listingEntities = listingRepository.findTop10ByListingCategoryOrderByListingIdDesc(category); //Find by id in descending order with a specific category
        }

        for (int i = offset; i < offset + limit && i < listingEntities.size(); i++) {
            Listing listing = listingEntities.get(i);
            Map<String, Object> listingMap = listing.toMap();
            listings.add(listingMap);
        }

        return listings;
    }

    public Map<String, Object> getListing(Long listingId) {
        if(listingId == null){
            throw new IllegalArgumentException("Listing ID cannot be null");
        }

        if(listingId <= 0){
            throw new IllegalArgumentException("Listing ID must be greater than 0");
        }

        Listing listing = listingRepository.findByListingId(listingId);

        return listing.toMap();
    }

    public void createListing(Long sellerId, String listingName, String listingDescription, double listingPrice, String[] listingPictures, String listingCategory) {
        //Validate input
        if(sellerId == null){
            throw new IllegalArgumentException("Seller ID cannot be null");
        }

        if(sellerId <= 0){
            throw new IllegalArgumentException("Seller ID must be greater than 0");
        }

        if(listingName == null || listingName.isEmpty()){
            throw new IllegalArgumentException("Listing name cannot be null or empty");
        }

        if(listingDescription == null || listingDescription.isEmpty()){
            throw new IllegalArgumentException("Listing description cannot be null or empty");
        }

        if(listingPrice < 0){
            throw new IllegalArgumentException("Listing price cannot be less than 0");
        }

        if(listingPictures == null || listingPictures.length == 0){
            throw new IllegalArgumentException("Listing pictures cannot be null or empty");
        }

        Listing listing = new Listing(sellerId, listingName, listingDescription, listingPrice, listingPictures, listingCategory);
        listingRepository.save(listing);
    }

    public void deleteListing(Long listingId) {
        if(listingId == null){
            throw new IllegalArgumentException("Listing ID cannot be null");
        }

        if(listingId <= 0){
            throw new IllegalArgumentException("Listing ID must be greater than 0");
        }

        Listing listing = listingRepository.findByListingId(listingId);

        if(listing == null){
            throw new IllegalArgumentException("Listing does not exist");
        }

        listingRepository.delete(listing);
    }

    public void updateListing(Listing listingNew) {
        //Validate input
        if(listingNew.getListingId() == null){
            throw new IllegalArgumentException("Listing ID cannot be null");
        }

        if(listingNew.getListingId() <= 0){
            throw new IllegalArgumentException("Listing ID must be greater than 0");
        }

        Listing listingOld = listingRepository.findByListingId(listingNew.getListingId());

        if(listingOld == null){
            throw new IllegalArgumentException("Listing does not exist");
        }

        if(!Objects.equals(listingOld.getSellerId(), listingNew.getSellerId())){
            throw new IllegalArgumentException("Seller id can't change");
        }

        listingRepository.save(listingNew);
    }

    public List<Listing> searchListing(String listingName){
        //Validate input
        if(listingName == null || listingName.isEmpty()){
            throw new IllegalArgumentException("Listing name cannot be empty or null");
        }

        return listingRepository.findByListingNameContaining(listingName);
    }

}
