function check_keyword() {
    const input = document.getElementById("searchInput");
    const minimumInput = document.getElementById("minPriceInput");
    const maximumInput = document.getElementById("maxPriceInput");
    if (input.value === "") return false;
    if (parseInt(minimumInput.value) < 0 || parseInt(maximumInput.value) < 0) return false;
    if (parseInt(minimumInput.value) > parseInt(maximumInput.value)) return false;

    return true;
}

function submit_form() {

    if (check_keyword()) document.forms['searchForm'].submit();
    else {
        const checkbox = document.getElementById("dealStatus");
        checkbox.checked = !checkbox.checked;
        alert("검색어를 입력하세요.");
    }
}

function checked_free(event) {
    const priceInput = document.getElementById("priceInput");
    const freeCheckbox = document.getElementById("freeCheckbox");

    if (priceInput.value === "0") {
        priceInput.value = "";
        priceInput.disabled = true;
        freeCheckbox.checked = true;
    }
}

function disabled_price(event) {
    const priceInput = document.getElementById("priceInput");

    if (event.checked) {
        priceInput.value = 0;
        priceInput.disabled = true;
    }
    else {
        priceInput.value = "";
        priceInput.disabled = false;
        priceInput.focus();
    }
}

function check_posting(event) {
    const titleInput = document.getElementById("titleInput");
    const contentTextarea = document.getElementById("contentTextarea");
    if (titleInput.value === "" || contentTextarea.value === "")    return false;
    return true;
}