<!DOCTYPE html>
<html>
    <head>
        <title>Ramsey's Secretary</title>
        <link rel="stylesheet" href="css/animstyle.css">
    </head>
    <body>
        <div class="header">
        <h1>Ramsey's Secretary</h1>
        <hr>
        <h2>Authorize the Secretary to view your 3rd party connections. </h2>
        <h2>For the Secretary to function properly, you must have your xbox account linked to your Discord.</h2>
        </div>

        <span class="warning">You may safely close this tab. You may also now deauthorise the Secretary if you wish. The role will be added to your profile soon.</span>

        <div class="permsAlign" style="background-color:rgb(69, 66, 73)">
            <span class="permsHeader" style="color:rgb(135, 236, 140); font-family: Windlass;">In Queue</span>
            <span class="permsHeader" style="color:rgb(159, 184, 199); font-family: Helvetica;">Your estimated wait time is</span>
            <span class="permsHeader" style="color:rgb(159, 184, 199); font-family: Helvetica;"><span id="time">0:00</span></span>
        </div>
        <div class="profileOutline">
            <image id="xboxImage" src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Xbox_one_logo.svg/1200px-Xbox_one_logo.svg.png" width=100 height=100/>
            <span class="xboxTextAlign"><span id=xboxName>${sourcedXboxName}</span></span>
         </div>
            <div class="footer">Built and Developed by <a href="discord.gg/XruZ6aYhmS">Aurorelyn#4695</a>
                <p>The "Ramsey's Secretary" app is a 3rd party extension and is in no way affiliated with Rare</p>
                <p>No user data is recorded or saved in any way other than that necessary for the bot to function as intended</p>
            </div>
        <script>
        function startTimer(duration, display) {
            var timer = duration, minutes, seconds;
            setInterval(function () {
             minutes = parseInt(timer / 60, 10);
             seconds = parseInt(timer % 60, 10);

             minutes = minutes < 10 ? "0" + minutes : minutes;
             seconds = seconds < 10 ? "0" + seconds : seconds;

             display.textContent = minutes + ":" + seconds;

            if (--timer < 0) {
                timer = duration;
            }
    }, 1000);
}

window.onload = function () {
    var fiveMinutes = 60 * ${queueLength},
        display = document.querySelector('#time');
        startTimer(fiveMinutes, display);
};
        </script>
    </body>
</html>