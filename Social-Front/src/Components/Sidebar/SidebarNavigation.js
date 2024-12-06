import HomeIcon from '@mui/icons-material/Home';
import ExploreIcon from '@mui/icons-material/Explore';
import ControlPointIcon from '@mui/icons-material/ControlPoint';
import NotificationsIcon from '@mui/icons-material/Notifications';
import ListAltIcon from '@mui/icons-material/ListAlt';
import Groups3Icon from '@mui/icons-material/Groups3';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import MessageIcon from '@mui/icons-material/Message';

export const navigationMenu=[
    {
        title:"Home",
        icon:<HomeIcon/>,
        path:"/"
    },
    {
        title:"Reels",
        icon:<ExploreIcon/>,
        path:"/reels"
    },
    {
        title:"Create Reels",
        icon:<ControlPointIcon/>,
        path:"/create-reels"
    },
    {
        title:"Notifications",
        icon:<NotificationsIcon/>,
        path:"/"
    },
    {
        title:"Message",
        icon:<MessageIcon/>,
        path:"/message"
    },
    {
        title:"Lists",
        icon:<ListAltIcon/>,
        path:"/"
    },
    {
        title:"Communities",
        icon:<Groups3Icon/>,
        path:"/"
    },
    {
        title:"Profile",
        icon:<AccountCircleIcon/>,
        path:"/profile/9"
    }
]