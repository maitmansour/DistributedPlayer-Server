module Mp3Player
{
	sequence<byte> ByteSeq;
    interface Player
    {
        string addNewFile(string title, string path, string artist, string album, string year, string rating);
        string findByFeature(string featureName, string featureValue );
        string deleteFile(string path);
        ByteSeq getFile();
        void setFile (string name,ByteSeq part,string current,string size);
    }
}
    
