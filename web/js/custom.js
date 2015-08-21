function mansory() {
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
}

document.addEventListener('DOMContentLoaded', function () {
    mansory();
});

function ajaxEvent(data) {
    if (data.status === "success") {
        mansory();
    }
}

$(document).ready(function () {
    $('.zoom-gallery').magnificPopup({
        delegate: 'a',
        type: 'image',
        closeOnContentClick: false,
        closeBtnInside: false,
        mainClass: 'mfp-with-zoom mfp-img-mobile',
        image: {
            verticalFit: true,
            titleSrc: function (item) {
                return item.el.attr('title') + ' &middot; <a class="image-source-link" href="' + item.el.attr('data-source') + '" target="_blank">image source</a>';
            }
        },
        gallery: {
            enabled: true
        },
        zoom: {
            enabled: true,
            duration: 300, // don't foget to change the duration also in CSS
            opener: function (element) {
                return element.find('img');
            }
        }

    });
});