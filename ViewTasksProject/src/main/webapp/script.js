document.addEventListener('DOMContentLoaded', () => {
    // Select HTML elements by their names and IDs
    const usernamefield = document.querySelector('input[name=username]');
    const pswdfield = document.querySelector('input[name=password]');
    const submitbtn = document.querySelector('input[type=submit]');
    const passdiv = document.getElementById('passdiv');
    const resetbtn = document.querySelector('input[type=reset]');

    // Initialize the submit button as disabled
    submitbtn.disabled = true;

    // Add an event listener for the username field when a key is released
    usernamefield.addEventListener("keyup", () => {
        var input = usernamefield.value;
        // Check if the input is empty and disable/enable the submit button accordingly
        if (input.length === 0)
            submitbtn.disabled = true;
        else
            submitbtn.disabled = false;
    });

    // Add an event listener for the password field when a key is released
    pswdfield.addEventListener("keyup", () => {
        var pswd = pswdfield.value;

        // Check if the password field is empty
        if (pswd.length === 0) {
            passdiv.innerText = ''; // Clear the text
        } else {
            // Check the password strength using a regular expression
            if (new RegExp(/^[a-z0-9]+$/i).test(pswd)){
                passdiv.innerText = 'EASY';
            } else if (pswd.length < 8) {
                passdiv.innerText = 'MEDIUM';
            } else {
                passdiv.innerText = 'HARD';
            }
        }
    });

    // Add an event listener for the reset button to clear the password field and text
    resetbtn.addEventListener("click", () => {
        pswdfield.value = ''; // Clear the password field
        passdiv.innerText = ''; // Clear the text in passdiv
        submitbtn.disabled = true;
    });
});
