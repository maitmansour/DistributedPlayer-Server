
<?php
require 'Ice.php';
require 'app/Player.php';
 
$ic = null;
try
{
    $ic = Ice\initialize();
    $base = $ic->stringToProxy("DistributedPlayer:default -p 10000");
    $player = Mp3Player\PlayerPrxHelper::checkedCast($base);
    if(!$player)
    {
        throw new RuntimeException("Invalid proxy");
    }

    if ($argc>1) {
        
    $functionChoice = $argv[1];

            switch ($functionChoice) {
                case 'addNewFile':
            echo $player->addNewFile($argv[2],$argv[3],$argv[4],$argv[5],$argv[6],$argv[7]);
                    break;
                
                case 'findByFeature':
            echo $player->findByFeature($argv[2],$argv[3]);
                    break;
                
                case 'deleteFile':
            echo $player->deleteFile($argv[2]);
                    break;

                default :
            echo "Function <".$functionChoice."> not defined, please use : \n-addNewFile [title, path, artist, album,  year, rating]\n-findByFeature [featureName, featureValue]\n-deleteFile [path]\n";
                    break;
            }
    }else{
            echo "Syntaxe error : please use php -f Client.php <Function name> [param1,param2,...]\n";

    }

}
catch(Exception $ex)
{
    echo $ex;
}
 
if($ic)
{
    $ic->destroy(); // Clean up
}
?>
