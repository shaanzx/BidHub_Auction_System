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

    // Search functionality
    document.getElementById('searchInput').addEventListener('input', function(e) {
        filterCategories();
    });

    // Status filter
    document.getElementById('statusFilter').addEventListener('change', function(e) {
        filterCategories();
    });

    // Sort functionality
    document.getElementById('sortBy').addEventListener('change', function(e) {
        sortCategories(e.target.value);
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
    handleResize();
});

// Category Modal Functions
let isEditing = false;
let currentRow = null;

function editCategory(button) {
    isEditing = true;
    currentRow = button.closest('tr');
    const modal = new bootstrap.Modal(document.getElementById('categoryModal'));

    // Get data from the table row
    const cells = currentRow.cells;
    document.getElementById('categoryCode').value = cells[0].textContent;
    document.getElementById('categoryName').value = cells[1].textContent;
    document.getElementById('description').value = cells[2].textContent;
    document.getElementById('statusSwitch').checked = cells[3].querySelector('input[type="checkbox"]').checked;

    // Update modal title
    document.getElementById('modalTitle').textContent = 'Edit Category';

    modal.show();
}

function saveCategory() {
    // Get form values
    const code = document.getElementById('categoryCode').value;
    const name = document.getElementById('categoryName').value;
    const description = document.getElementById('description').value;
    const isActive = document.getElementById('statusSwitch').checked;

    // Basic validation
    if (!code || !name || !description) {
        alert('Please fill in all required fields');
        return;
    }

    if (isEditing) {
        // Update existing row
        const cells = currentRow.cells;
        cells[0].textContent = code;
        cells[1].textContent = name;
        cells[2].textContent = description;
        cells[3].querySelector('input[type="checkbox"]').checked = isActive;
        cells[3].querySelector('label').textContent = isActive ? 'Active' : 'Inactive';
    } else {
        // Add new row
        const table = document.querySelector('table tbody');
        const newRow = table.insertRow();
        newRow.innerHTML = `
            <td>${code}</td>
            <td>${name}</td>
            <td>${description}</td>
            <td>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" ${isActive ? 'checked' : ''} onchange="toggleStatus(this)">
                    <label class="form-check-label">${isActive ? 'Active' : 'Inactive'}</label>
                </div>
            </td>
            <td>
                <button class="btn btn-sm btn-outline-primary me-1" onclick="editCategory(this)">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger" onclick="deleteCategory(this)">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
    }

    // Reset form and close modal
    document.getElementById('categoryForm').reset();
    bootstrap.Modal.getInstance(document.getElementById('categoryModal')).hide();
    isEditing = false;
    currentRow = null;
}

function toggleStatus(checkbox) {
    const label = checkbox.nextElementSibling;
    label.textContent = checkbox.checked ? 'Active' : 'Inactive';
}

function deleteCategory(button) {
    if (confirm('Are you sure you want to delete this category?')) {
        button.closest('tr').remove();
    }
}

function filterCategories() {
    const searchText = document.getElementById('searchInput').value.toLowerCase();
    const statusFilter = document.getElementById('statusFilter').value;
    const rows = document.getElementById('categoryTableBody').getElementsByTagName('tr');

    for (let row of rows) {
        const code = row.cells[0].textContent.toLowerCase();
        const name = row.cells[1].textContent.toLowerCase();
        const description = row.cells[2].textContent.toLowerCase();
        const isActive = row.cells[3].querySelector('input[type="checkbox"]').checked;
        const status = isActive ? 'active' : 'inactive';

        const matchesSearch = code.includes(searchText) ||
            name.includes(searchText) ||
            description.includes(searchText);

        const matchesStatus = !statusFilter || status === statusFilter;

        row.style.display = matchesSearch && matchesStatus ? '' : 'none';
    }
}

function sortCategories(criteria) {
    const tbody = document.getElementById('categoryTableBody');
    const rows = Array.from(tbody.getElementsByTagName('tr'));

    rows.sort((a, b) => {
        let aValue, bValue;

        switch(criteria) {
            case 'code':
                aValue = a.cells[0].textContent;
                bValue = b.cells[0].textContent;
                break;
            case 'name':
                aValue = a.cells[1].textContent;
                bValue = b.cells[1].textContent;
                break;
            case 'status':
                aValue = a.cells[3].querySelector('input[type="checkbox"]').checked;
                bValue = b.cells[3].querySelector('input[type="checkbox"]').checked;
                return bValue - aValue; // Active first
        }

        return aValue.localeCompare(bValue);
    });

    // Clear and re-append sorted rows
    rows.forEach(row => tbody.appendChild(row));
}

// Reset modal state when it's closed
document.getElementById('categoryModal').addEventListener('hidden.bs.modal', function () {
    document.getElementById('categoryForm').reset();
    document.getElementById('modalTitle').textContent = 'Add New Category';
    isEditing = false;
    currentRow = null;
});