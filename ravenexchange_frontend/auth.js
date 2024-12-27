const BASE_URL = 'http://localhost:8080/api/auth'; // Replace with your backend URL

// Login function
async function loginUser(login, password) {
    try {
        const response = await fetch(`${BASE_URL}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ login, password }),
        });

        if (response.ok) {
            const data = await response.json();
            console.log('Login successful:', data);

            // Redirect to profile or home page
            window.location.href = 'home.html';
        } else {
            const error = await response.json();
            alert(error.message || 'Login failed');
        }
    } catch (err) {
        console.error('Error logging in:', err);
        alert('An error occurred while logging in.');
    }
}

// Register function
async function registerUser(userDetails) {
    try {
        const response = await fetch(`${BASE_URL}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userDetails),
        });

        if (response.ok) {
            const data = await response.json();
            console.log('Registration successful:', data);
            alert('Registration successful! Please log in.');
            window.location.href = 'login.html'; // Redirect to login
        } else {
            const error = await response.json();
            alert(error.message || 'Registration failed');
        }
    } catch (err) {
        console.error('Error registering user:', err);
        alert('An error occurred during registration.');
    }
}

async function forgotPassword(email) {
    try {
        const response = await fetch(`${BASE_URL}/forgot-password`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email }),
        });

        if (response.ok) {
            const data = await response.json();
            alert(data.message || 'Password reset link sent successfully!');
        } else {
            const error = await response.json();
            alert(error.message || 'Failed to send reset link');
        }
    } catch (err) {
        console.error('Error sending reset link:', err);
        alert('An error occurred while sending the reset link.');
    }
}

// Example usage: Attach these to forms in your HTML
// loginUser('john_doe', 'securepassword123');
// registerUser({
//     username: 'john_doe',
//     firstname: 'John',
//     lastname: 'Doe',
//     email: 'john@example.com',
//     password: 'securepassword123',
//     university_email: 'john@cmail.carleton.ca',
// });
