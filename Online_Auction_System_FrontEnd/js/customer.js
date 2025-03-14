document.addEventListener('DOMContentLoaded', function() {
    // Sidebar toggle
    document.getElementById('sidebarCollapse').addEventListener('click', function() {
        document.getElementById('sidebar').classList.toggle('active');
    });

    // Initialize all dropdowns
    var dropdowns = document.querySelectorAll('.dropdown-toggle');
    dropdowns.forEach(function(dropdown) {
        new bootstrap.Dropdown(dropdown);
    });

    // Add hover effect to sidebar items
    var sidebarItems = document.querySelectorAll('#sidebar ul li a');
    sidebarItems.forEach(function(item) {
        item.addEventListener('mouseenter', function() {
            this.style.transition = 'all 0.3s';
        });
    });

    // Handle responsive behavior
    function handleResize() {
        if (window.innerWidth <= 768) {
            document.getElementById('sidebar').classList.add('active');
        } else {
            document.getElementById('sidebar').classList.remove('active');
        }
    }

    window.addEventListener('resize', handleResize);
    handleResize(); // Call on initial load
});

// Admin Modal Functions
let isEditing = false;
let currentRow = null;

function editAdmin(button) {
    isEditing = true;
    currentRow = button.closest('tr');
    const modal = new bootstrap.Modal(document.getElementById('adminModal'));

    // Get data from the table row
    const cells = currentRow.cells;
    document.getElementById('name').value = cells[0].textContent;
    document.getElementById('email').value = cells[1].textContent;
    document.getElementById('address').value = cells[2].textContent;
    document.getElementById('contact').value = cells[3].textContent;
    document.getElementById('nic').value = cells[4].textContent;

    // Clear password fields for editing
    document.getElementById('password').value = '';
    document.getElementById('confirmPassword').value = '';

    // Update modal title
    document.getElementById('modalTitle').textContent = 'Edit Admin';

    modal.show();
}

function saveAdmin() {
    // Get form values
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const address = document.getElementById('address').value;
    const contact = document.getElementById('contact').value;
    const nic = document.getElementById('nic').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    // Basic validation
    if (!name || !email || !address || !contact || !nic) {
        alert('Please fill in all required fields');
        return;
    }

    if (!isEditing && password !== confirmPassword) {
        alert('Passwords do not match');
        return;
    }

    if (isEditing) {
        // Update existing row
        const cells = currentRow.cells;
        cells[0].textContent = name;
        cells[1].textContent = email;
        cells[2].textContent = address;
        cells[3].textContent = contact;
        cells[4].textContent = nic;
    } else {
        // Add new row
        const table = document.querySelector('table tbody');
        const newRow = table.insertRow();
        newRow.innerHTML = `
            <td>${name}</td>
            <td>${email}</td>
            <td>${address}</td>
            <td>${contact}</td>
            <td>${nic}</td>
            <td><span class="badge bg-success">Active</span></td>
            <td>
                <button class="btn btn-sm btn-outline-primary me-1" onclick="editAdmin(this)">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
    }

    // Reset form and close modal
    document.getElementById('adminForm').reset();
    bootstrap.Modal.getInstance(document.getElementById('adminModal')).hide();
    isEditing = false;
    currentRow = null;
}

// Reset modal state when it's closed
document.getElementById('adminModal').addEventListener('hidden.bs.modal', function () {
    document.getElementById('adminForm').reset();
    document.getElementById('modalTitle').textContent = 'Add New Admin';
    isEditing = false;
    currentRow = null;
});

// Status toggle
function toggleStatus(checkbox) {
    const label = checkbox.nextElementSibling;
    label.textContent = checkbox.checked ? 'Active' : 'Inactive';
}