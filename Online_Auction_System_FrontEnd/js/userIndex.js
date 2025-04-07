// Handle login form submission
document.getElementById('loginForm')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    // Here you would typically make an API call to authenticate
    console.log('Login attempt:', { email, password });

    // Mock successful login
    handleSuccessfulLogin({ name: 'John Doe', email: email });
});

// Handle signup form submission
document.getElementById('signupForm')?.addEventListener('submit', function(e) {
    e.preventDefault();
    const name = document.getElementById('signupName').value;
    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;
    const confirmPassword = document.getElementById('signupConfirmPassword').value;

    if (password !== confirmPassword) {
        alert('Passwords do not match!');
        return;
    }

    // Here you would typically make an API call to register
    console.log('Signup attempt:', { name, email, password });

    // Mock successful signup
    handleSuccessfulLogin({ name: name, email: email });
});

// Handle successful login/signup
function handleSuccessfulLogin(user) {
    // Update UI for logged-in state
    const authButtons = document.getElementById('authButtons');
    if (authButtons) {
        authButtons.innerHTML = `
            <div class="dropdown">
                <button class="btn btn-outline-primary dropdown-toggle" type="button" id="userDropdown" data-bs-toggle="dropdown">
                    <i class="bi bi-person-circle"></i> ${user.name}
                </button>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><a class="dropdown-item" href="/profile">My Profile</a></li>
                    <li><a class="dropdown-item" href="/my-bids">My Bids</a></li>
                    <li><a class="dropdown-item" href="/watchlist">Watchlist</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#" onclick="handleLogout()">Logout</a></li>
                </ul>
            </div>
        `;
    }

    // Close the modal
    const modalElement = document.querySelector('.modal.show');
    if (modalElement) {
        const modal = bootstrap.Modal.getInstance(modalElement);
        modal.hide();
    }
}

// Handle logout
function handleLogout() {
    // Here you would typically make an API call to logout

    // Reset UI to logged-out state
    const authButtons = document.getElementById('authButtons');
    if (authButtons) {
        authButtons.innerHTML = `
            <button class="btn btn-outline-primary me-2" data-bs-toggle="modal" data-bs-target="#loginModal">Sign In</button>
            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#signupModal">Register</button>
        `;
    }
}

// Initialize tooltips
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});

// Add countdown timer functionality for auctions
function updateCountdowns() {
    const now = new Date().getTime();
    document.querySelectorAll('[data-countdown]').forEach(el => {
        const endTime = new Date(el.dataset.countdown).getTime();
        const distance = endTime - now;

        if (distance < 0) {
            el.innerHTML = 'Auction Ended';
            return;
        }

        const days = Math.floor(distance / (1000 * 60 * 60 * 24));
        const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((distance % (1000 * 60)) / 1000);

        el.innerHTML = `${days}d ${hours}h ${minutes}m ${seconds}s`;
    });
}

// Update countdowns every second
setInterval(updateCountdowns, 1000);

// Handle bid form submissions
document.querySelectorAll('.bid-form')?.forEach(form => {
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        const bidAmount = this.querySelector('input[name="bidAmount"]').value;
        const lotId = this.dataset.lotId;

        // Here you would typically make an API call to place the bid
        console.log('Bid placed:', { lotId, bidAmount });

        // Show success message
        alert('Bid placed successfully!');
    });
});