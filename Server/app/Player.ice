module Mp3Player
{
    interface Player
    {
        string addNewFile(string title, string path, string artist, string album, string year, string rating);
        string findByFeature(string featureName, string featureValue );
        string deleteFile(string path);
    }
}
    
