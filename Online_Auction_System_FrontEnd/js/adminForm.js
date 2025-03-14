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