$(window).ready(function mansory () {
    // init Isotope
    var $grid = $('.grid').isotope({
        itemSelector: '.grid-item',
        //percentPosition: true,
        transitionDuration: '0.6s',
        masonry: {
            columnHeight: '.grid-item'
        }
    });
    // layout Isotope after each image loads
    $grid.imagesLoaded().progress(function () {
        $grid.isotope('layout');
    });

});
