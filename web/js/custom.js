$(window).load(function () {
    // Animate loader off screen
    $(".se-pre-con").fadeOut("slow");
    $('#contents').css('visibility', 'visible').hide().fadeIn("fast");
});

function mansory() {
    // init Isotope
    var $grid = $('.grid').isotope({
        itemSelector: '.grid-item',
        //percentPosition: true,
        transitionDuration: '1s',
        masonry: {
            columnHeight: '.grid-item'
        }
    });

    // layout Isotope after each image loads
    $grid.imagesLoaded().progress(function () {
        $grid.delay(1500).isotope('layout');
    });
}

document.addEventListener('DOMContentLoaded', function () {
    mansory();
});

function ajaxEvent(data) {
//    $(".se-pre-con").fadeIn("fast");
    $(".se-pre-con").show();
    if (data.status === "success") {
        $(".se-pre-con").fadeOut("slow");
        mansory();
    }
}

$(document).ready(function () {
    $('body > ul > li.alert.alert-danger.alert-dismissible')
            .prepend('<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');

    $('.zoom-gallery').magnificPopup({
        delegate: 'a',
        type: 'image',
        closeOnContentClick: false,
        closeBtnInside: false,
        mainClass: 'mfp-with-zoom mfp-img-mobile',
        image: {
            verticalFit: true,
            titleSrc: function (item) {
                return item.el.attr('title') + ' &middot; <a class="image-source-link" href="'
                        + item.el.attr('data-source') + '" target="_blank">image source</a>';
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

    $('.gallery').each(function () {
        $(this).magnificPopup({
            delegate: 'a',
            gallery: {
                enabled: true
            },
            type: 'image'
        });
    });
    
    if ($('#isProfessional').prop('checked')) {
        $('#professionalSection').toggle("slow");
    }
    $('#isProfessional').change(function () {
        $('#professionalSection').toggle("slow");
    });
});