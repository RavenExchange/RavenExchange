// Example data store for listings
let listings = [
    { id: 1, title: 'Carleton Hoodie', price: 40, description: 'A cozy hoodie.', image: 'item1.jpg' },
    { id: 2, title: 'Used Textbook', price: 30, description: 'Math 101 textbook.', image: 'item2.jpg' },
];

let editingListingId = null; // To track if a listing is being edited

// Render listings dynamically
function renderListings() {
    const listingsContainer = document.getElementById('listingsContainer');
    listingsContainer.innerHTML = '';
    listings.forEach(listing => {
        const listingElement = document.createElement('div');
        listingElement.className = 'listing-item';
        listingElement.innerHTML = `
            <img src="${listing.image}" alt="${listing.title}">
            <div class="listing-details">
                <h3>${listing.title}</h3>
                <p>$${listing.price}</p>
                <p>${listing.description}</p>
                <button onclick="populateForm(${listing.id})">Edit</button>
                <button onclick="deleteListing(${listing.id})">Delete</button>
            </div>
        `;
        listingsContainer.appendChild(listingElement);
    });
}

// Populate form with listing details for editing
function populateForm(listingId) {
    const listing = listings.find(item => item.id === listingId);
    if (listing) {
        document.getElementById('title').value = listing.title;
        document.getElementById('price').value = listing.price;
        document.getElementById('description').value = listing.description;
        document.getElementById('image').value = ''; // File inputs can't set a value for security reasons
        document.getElementById('formTitle').textContent = 'Edit Listing';
        editingListingId = listingId; // Track which listing is being edited
    }
}

// Save or update a listing
document.getElementById('listingForm').addEventListener('submit', function (event) {
    event.preventDefault();
    const title = document.getElementById('title').value;
    const price = document.getElementById('price').value;
    const description = document.getElementById('description').value;
    const imageFile = document.getElementById('image').files[0];

    const image = imageFile ? URL.createObjectURL(imageFile) : 'default.jpg';

    if (editingListingId !== null) {
        // Update existing listing
        const listing = listings.find(item => item.id === editingListingId);
        if (listing) {
            listing.title = title;
            listing.price = price;
            listing.description = description;
            listing.image = imageFile ? image : listing.image; // Update image only if a new one is selected
            alert('Listing updated successfully!');
        }
        editingListingId = null; // Reset editing mode
        document.getElementById('formTitle').textContent = 'Create a New Listing';
    } else {
        // Create a new listing
        const newListing = {
            id: Date.now(),
            title,
            price,
            description,
            image,
        };
        listings.push(newListing);
        alert('Listing created successfully!');
    }

    renderListings(); // Re-render listings
    this.reset(); // Clear the form
});

// Delete a listing
function deleteListing(id) {
    listings = listings.filter(listing => listing.id !== id);
    renderListings();
    alert('Listing deleted.');
}

// Initialize the page
document.addEventListener('DOMContentLoaded', renderListings);
