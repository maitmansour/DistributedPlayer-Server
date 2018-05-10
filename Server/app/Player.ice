module Mp3Player
{
	sequence<byte> ByteSeq;
    interface Player
    {
        string addNewFile(string title, string artist, string album, string year, string filename,string image);
        string findByFeature(string featureName, string featureValue );
        string getAllMusic();
        string deleteFile(string path);
        ByteSeq getFile(string name,string part);
        void setFile (string name,ByteSeq part,string current,string size);
    }
}
    
