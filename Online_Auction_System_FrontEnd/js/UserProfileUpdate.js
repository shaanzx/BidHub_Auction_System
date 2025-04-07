$(document).ready(function() {
    // Show profile modal when clicking profile dropdown item
    $('a.dropdown-item:contains("Profile")').click(function(e) {
        e.preventDefault();
        loadUserProfile();
        $('#userProfileModal').modal('show');
    });

    // Toggle password visibility
    $('#togglePassword').click(function() {
        const passwordInput = $('#userPassword');
        const icon = $('#passwordToggleIcon');

        if (passwordInput.attr('type') === 'password') {
            passwordInput.attr('type', 'text');
            icon.removeClass('bi-eye-slash').addClass('bi-eye');
        } else {
            passwordInput.attr('type', 'password');
            icon.removeClass('bi-eye').addClass('bi-eye-slash');
        }
    });

    // Handle form submission
    $('#saveUserProfile').click(function() {
        const form = document.getElementById('userProfileForm');

        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
            $(form).addClass('was-validated');
            return;
        }

        // Collect form data
        const formData = {
            name: $('#userName').val(),
            email: $('#userEmail').val(),
            address: $('#userAddress').val(),
            contact: $('#userContact').val(),
            nic: $('#userNic').val(),
            role: $('#userRole').val(),
            status: $('#userStatus').val()
        };

        // Only include password if it was changed
        if ($('#userPassword').val()) {
            formData.password = $('#userPassword').val();
        }

        // Send data to server
        updateUserProfile(formData);
    });

    function loadUserProfile() {
        const token = localStorage.getItem('authToken');

        $.ajax({
            url: '/api/v1/users/profile',
            type: 'GET',
            headers: { 'Authorization': token },
            success: function(response) {
                if (response.code === '00') {
                    const user = response.data;

                    // Populate form fields
                    $('#userName').val(user.name);
                    $('#userEmail').val(user.email);
                    $('#userAddress').val(user.address);
                    $('#userContact').val(user.contact);
                    $('#userNic').val(user.nic);
                    $('#userRole').val(user.role);
                    $('#userStatus').val(user.status);

                    // Show admin fields if user is admin
                    if (user.role === 'ADMIN') {
                        $('.admin-only').show();
                    } else {
                        $('.admin-only').hide();
                    }
                }
            },
            error: function(xhr) {
                Swal.fire('Error', 'Failed to load profile', 'error');
            }
        });
    }

    function updateUserProfile(userData) {
        const token = localStorage.getItem('authToken');

        $.ajax({
            url: '/api/v1/users/update-profile',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(userData),
            headers: { 'Authorization': token },
            success: function(response) {
                if (response.code === '00') {
                    Swal.fire({
                        title: 'Success!',
                        text: 'Profile updated successfully',
                        icon: 'success',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        $('#userProfileModal').modal('hide');
                        // Refresh user info if needed
                        if (typeof refreshUserInfo === 'function') {
                            refreshUserInfo();
                        }
                    });
                } else {
                    Swal.fire('Error', response.message || 'Update failed', 'error');
                }
            },
            error: function(xhr) {
                const errorMsg = xhr.responseJSON?.message || 'Failed to update profile';
                Swal.fire('Error', errorMsg, 'error');
            }
        });
    }
});