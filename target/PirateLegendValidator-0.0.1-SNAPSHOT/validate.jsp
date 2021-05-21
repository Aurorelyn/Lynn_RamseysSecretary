<!DOCTYPE html>
<html>
    <head>
        <title>Ramsey's Secretary</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="header">
        <h1>Ramsey's Secretary</h1>
        <hr>
        <h2>Authorize the Secretary to view your 3rd party connections. </h2>
        <h2>For the Secretary to function properly, you must have your xbox account linked to your Discord.</h2>
        </div>
       
        <a id="login" class="authButton" style="display:none;" href="https://discord.com/api/oauth2/authorize?client_id=837022531378085930&redirect_uri=https%3A%2F%2Framseyssecretary.herokuapp.com%2Fvalidate&response_type=code&scope=connections%20identify">Identify yourself stranger</a>
        <div id="showOnLoad" class="permsAlign" style="display:none;">
            <span class="permsHeader">Requested Permissions:</span>
            <span class="perm">> 3rd Party connections:</span>
            <span class="permDesc">This permission is used to verify your identity via your linked xbox account.</span>
        </div>
        <form id="useAccount" action="queue" method="GET">

            <div id="hideOnLoad" class="profileOutline" style="display:block;" onclick="submitForm">
                <image id="xboxImage" src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Xbox_one_logo.svg/1200px-Xbox_one_logo.svg.png" width=100 height=100/>
                <span class="xboxTextAlign"><span id=xboxName></span></span>
                <span class="xboxTextAlign">Use this account ></span>
             </div>
        </form>

            <div class="footer">Built and Developed by <a href="discord.gg/XruZ6aYhmS">Aurorelyn#4695</a>
                <p>The "Ramsey's Secretary" app is a 3rd party extension and is in no way affiliated with Rare</p>
                <p>No user data is recorded or saved in any way other than that necessary for the bot to function as intended</p>
            </div>
    <script>
        window.onload = () => {
            const frag = new URLSearchParams(window.location.search);
            const accessToken = frag.get('code');
             
            if(!accessToken){
                document.getElementById("login").style.display = 'inline-block';
                document.getElementById("hideOnLoad").style.display = 'none';
                return document.getElementById("showOnLoad").style.display = 'block';
                
            }else{
                document.getElementById("hideOnLoad").style.display = 'block';
            }
            
            document.getElementById("xboxName").innerText = "${sourcedXboxName}";
            document.getElementById("hideOnLoad").onclick = function(){document.getElementById('useAccount').submit();}
          

        }
    </script>
    </body>
</html>