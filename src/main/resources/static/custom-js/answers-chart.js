$(document).ready(function () {
    "use strict";

    let charts = document.querySelectorAll(".answers-chart");

    charts.forEach(function (item) {

        let one = parseInt(item.getAttribute("one"));
        let two = parseInt(item.getAttribute("two"));
        let three = parseInt(item.getAttribute("three"));
        let four = parseInt(item.getAttribute("four"));
        let five = parseInt(item.getAttribute("five"));

        if (!isNaN(one) && !isNaN(two) && !isNaN(three) && !isNaN(four) && !isNaN(five)) {
            let barData = {
                labels: ["Кошулбайм", "", "", "", "Кошулам"],
                datasets: [{
                    label: 'Жооптор',
                    data: [one, two, three, four, five],
                    backgroundColor: [
                        'rgba(252, 69, 3, 0.8)',
                        'rgba(252, 140, 3, 0.8)',
                        'rgba(252, 235, 3, 0.8)',
                        'rgba(194, 252, 3, 0.8)',
                        'rgba(103, 252, 3, 0.8)'
                    ],
                    borderColor: [
                        'rgba(252, 69, 3, 1)',
                        'rgba(252, 140, 3, 1)',
                        'rgba(252, 235, 3, 1)',
                        'rgba(194, 252, 3, 1)',
                        'rgba(103, 252, 3, 1)'
                    ],
                    borderWidth: 1
                }]
            };

            let barOptions = {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            };

            let currentChartCanvas = item.getContext("2d");
            let currentChart = new Chart(currentChartCanvas, {
                type: 'bar',
                data: barData,
                options: barOptions
            });
        }
    });


});
