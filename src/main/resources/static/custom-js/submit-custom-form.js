$(document).ready(function () {
    "use strict";

    const questions = document.querySelectorAll(".form-question");

    const customForm = document.getElementById("custom-form");

    customForm.addEventListener("submit", function (e) {
        e.preventDefault();

        let surveyId = document.getElementById('surveyId').value;
        let teacherId = document.getElementById('teacherId').value;
        let answers = [];

        questions.forEach(function (item) {
            let question = item.name.substring(1);
            let radios = document.querySelectorAll("input[type='radio'][name='" + question + "']");
            let selectedValue;
            radios.forEach(function (radio) {
                if (radio.checked) {
                    selectedValue = radio.value;
                }
            });
            if (selectedValue == null) {
            } else {

                let questionPair = {
                    question: item.value,
                    value: selectedValue
                }

                answers.push(questionPair);
            }
        });

        let request = {
            surveyId: surveyId,
            teacherId: teacherId,
            answers: answers
        }

        if (answers.length === 0) {
            alert("Бардык суроолорду белгилеңиз!");
        } else {


            $.ajax({
                url: "/survey-submit",
                type: "POST",
                data: JSON.stringify(request),
                contentType: "application/json",
                processData: false,
                success: function (response) {
                    window.location.reload();
                },
                error: function (xhr) {
                    try {
                        const errorData = JSON.parse(xhr.responseText);
                        if (errorData.hasOwnProperty("error")) {
                            alert(errorData.error);
                        } else {
                            alert('Что то пошло не так');
                        }
                    } catch (e) {
                        alert('Что то пошло не так');
                    }
                    window.location.reload();
                }
            })
        }
    })
});
