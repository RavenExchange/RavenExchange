const BASE_URL = 'http://localhost:8080/api/listings'; // Backend URL

// Fetch listing details by ID
async function fetchListingDetails(listingId) {
    try {
        const response = await fetch(`${BASE_URL}/${listingId}`); // Call backend API
        if (!response.ok) {
            throw new Error('Listing not found');
        }
        const listing = await response.json();
        renderListingDetails(listing); // Populate the frontend with data
    } catch (error) {
        console.error('Error fetching listing:', error);
        document.getElementById('listing').innerHTML = `<p>${error.message}</p>`;
    }
}

// Render listing details on the page
function renderListingDetails(listing) {
    const listingContainer = document.getElementById('listing');
    listingContainer.innerHTML = `
        <img src="${listing.image}" alt="${listing.title}" class="listing-image">
        <h2 class="listing-title">${listing.title}</h2>
        <p class="listing-price">${listing.price}</p>
        <p class="listing-description">${listing.description}</p>
        <p class="listing-seller"><strong>Seller:</strong> ${listing.seller}</p>
    `;
}

// Contact seller functionality
async function contactSeller(sellerId) {
    const currentUserId = sessionStorage.getItem('userId'); // Example: Get userId from session
    if (!currentUserId) {
        alert('Please log in to contact the seller.');
        window.location.href = 'login.html';
        return;
    }

    // Redirect to the conversation page (assuming conversation setup exists)
    window.location.href = `messages.html?sellerId=${sellerId}`;
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const listingId = urlParams.get('id');
    if (listingId) {
        fetchListingDetails(listingId); // Fetch and display listing
    } else {
        document.getElementById('listing').innerHTML = '<p>Listing not found.</p>';
    }
});
