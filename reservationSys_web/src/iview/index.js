import { Rate, Card, Button, Input, InputNumber, Alert, Table, Menu, Row, Col, Icon,  Select, Option, Checkbox, Form, FormItem, Modal, MenuItem, MenuGroup, Submenu, Tabs, TabPane, Radio, RadioGroup,Spin, Page,DatePicker, Drawer, Upload,Tree,Collapse, Panel, Tag, Cascader, Poptip,Dropdown,DropdownItem, DropdownMenu,Badge, Tooltip, Avatar, AutoComplete,  Switch} from 'iview';

const iview = {
    install: function (Vue) {
        Vue.component('Rate', Rate);
        Vue.component('Card', Card);
        Vue.component('Badge', Badge);
        Vue.component('Avatar', Avatar);
        Vue.component('Tooltip', Tooltip);
        Vue.component('Button', Button);
        Vue.component('Table', Table);
        Vue.component('Menu', Menu);
        Vue.component('Submenu', Submenu);
        Vue.component('MenuGroup', MenuGroup);
        // i-switch
        Vue.component('iSwitch',Switch);
        // Vue.component('iCol',Col);
        Vue.component('MenuItem', MenuItem);
        Vue.component('Checkbox', Checkbox);
		Vue.component('Form', Form);
		Vue.component('FormItem', FormItem);
		Vue.component('Spin', Spin);
		Vue.component('Alert', Alert);
        Vue.component('Row', Row);
        Vue.component('Col', Col);
        Vue.component('Icon', Icon);
        Vue.component('Input', Input);
        Vue.component('InputNumber', InputNumber);
		Vue.component('Page', Page);
        Vue.component('Select', Select);
        Vue.component('Option', Option);
		Vue.component('Modal', Modal);
		Vue.component('DatePicker', DatePicker);
		Vue.component('Drawer', Drawer);
        Vue.component('Tabs', Tabs);
        Vue.component('TabPane', TabPane);
        Vue.component('Radio', Radio);
        Vue.component('Upload', Upload);
        Vue.component('Tree', Tree);
        Vue.component('Collapse', Collapse);
        Vue.component('Panel', Panel);
        Vue.component('Tag', Tag);
        Vue.component('Cascader', Cascader);
        Vue.component('Poptip', Poptip);
        Vue.component('RadioGroup', RadioGroup);
        Vue.component('Dropdown', Dropdown);
        Vue.component('DropdownItem', DropdownItem);
        Vue.component('DropdownMenu', DropdownMenu);
        Vue.component('AutoComplete', AutoComplete);
    }
}

export default iview
