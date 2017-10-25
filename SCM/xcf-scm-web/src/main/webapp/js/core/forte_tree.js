(function(ns) {
  ns.tree = {
    init : function(treeId, data, autoExpand) {
      var setting = {
        check : {
          enable : true
        },
        data : {
          simpleData : {
            enable : true
          }
        },
        view : {
          expandSpeed : "",
          showLine : false,
          showIcon : true
        }
      };
      var treeObj = $.fn.zTree.init($(treeId), setting, data);
      if (ns.isEmpty(autoExpand)) {
        autoExpand = true;
      }
      if (autoExpand) {
        treeObj.expandAll(true);
      }
    },

    convertTreeDS : function(treeDS, ret) {
      for ( var i = 0, len = treeDS.length; i < len; i++) {
        var item = treeDS[i];
        if (item.id == 0) {
          ret = ret || [];
          ret.push({
            id : 0,
            pId : 0,
            name : item.text,
            checked : item.checkstate != 0
          });
          if (item.hasChildren) {
            for ( var j = 0, len2 = item.ChildNodes.length; j < len2; j++) {
              var item2 = item.ChildNodes[j];
              ret.push({
                id : item2.id,
                pId : item.id,
                name : item2.text || "",
                checked : item2.checkstate != 0
              });
              if (item2.hasChildren) {
                convertTreeDS(item2.ChildNodes, ret);
              }
            }
          }
        }
      }
      return ret;
    },

    getCheckedNodes : function(treeId, onlyId) {
      var checkedNodes = $.fn.zTree.getZTreeObj(treeId).getCheckedNodes();
      if (onlyId) {
        var checkNodeIDs = [];
        for ( var i = 0, len = checkedNodes.length; i < len; i++) {
          var node = checkedNodes[i];
          checkNodeIDs.push(node.id);
        }
        return checkNodeIDs;
      } else {
        return checkedNodes;
      }
    }
  };
})(FORTE);