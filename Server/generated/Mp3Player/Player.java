// **********************************************************************
//
// Copyright (c) 2003-2017 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.7.0
//
// <auto-generated>
//
// Generated from file `Player.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Mp3Player;

public interface Player extends com.zeroc.Ice.Object
{
    String addNewFile(String title, String artist, String album, String year, String filename, String image, com.zeroc.Ice.Current current);

    String findByFeature(String featureName, String featureValue, com.zeroc.Ice.Current current);

    String getAllMusic(com.zeroc.Ice.Current current);

    String deleteFile(String path, com.zeroc.Ice.Current current);

    byte[] getFile(String name, String part, com.zeroc.Ice.Current current);

    void setFile(String name, byte[] part, String current, String size, com.zeroc.Ice.Current current_);

    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::Mp3Player::Player"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::Mp3Player::Player";
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_addNewFile(Player obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_title;
        String iceP_artist;
        String iceP_album;
        String iceP_year;
        String iceP_filename;
        String iceP_image;
        iceP_title = istr.readString();
        iceP_artist = istr.readString();
        iceP_album = istr.readString();
        iceP_year = istr.readString();
        iceP_filename = istr.readString();
        iceP_image = istr.readString();
        inS.endReadParams();
        String ret = obj.addNewFile(iceP_title, iceP_artist, iceP_album, iceP_year, iceP_filename, iceP_image, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeString(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_findByFeature(Player obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_featureName;
        String iceP_featureValue;
        iceP_featureName = istr.readString();
        iceP_featureValue = istr.readString();
        inS.endReadParams();
        String ret = obj.findByFeature(iceP_featureName, iceP_featureValue, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeString(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getAllMusic(Player obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        String ret = obj.getAllMusic(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeString(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_deleteFile(Player obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_path;
        iceP_path = istr.readString();
        inS.endReadParams();
        String ret = obj.deleteFile(iceP_path, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeString(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getFile(Player obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_name;
        String iceP_part;
        iceP_name = istr.readString();
        iceP_part = istr.readString();
        inS.endReadParams();
        byte[] ret = obj.getFile(iceP_name, iceP_part, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeByteSeq(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setFile(Player obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_name;
        byte[] iceP_part;
        String iceP_current;
        String iceP_size;
        iceP_name = istr.readString();
        iceP_part = istr.readByteSeq();
        iceP_current = istr.readString();
        iceP_size = istr.readString();
        inS.endReadParams();
        obj.setFile(iceP_name, iceP_part, iceP_current, iceP_size, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    final static String[] _iceOps =
    {
        "addNewFile",
        "deleteFile",
        "findByFeature",
        "getAllMusic",
        "getFile",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "setFile"
    };

    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_addNewFile(this, in, current);
            }
            case 1:
            {
                return _iceD_deleteFile(this, in, current);
            }
            case 2:
            {
                return _iceD_findByFeature(this, in, current);
            }
            case 3:
            {
                return _iceD_getAllMusic(this, in, current);
            }
            case 4:
            {
                return _iceD_getFile(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 7:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 8:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 9:
            {
                return _iceD_setFile(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
